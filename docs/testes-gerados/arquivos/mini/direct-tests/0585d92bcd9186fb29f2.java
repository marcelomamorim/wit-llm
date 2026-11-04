/*
 * @test
 * @summary Test default constructor of SubmissionPublisher
 * @run main SubmissionPublisherWitupTest
 */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class SubmissionPublisherWitupTest {
    public static void main(String[] args) throws Exception {
        // Create SubmissionPublisher with default constructor
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        // Subscribe a simple Subscriber that requests 1 item
        TestSubscriber subscriber = new TestSubscriber();
        publisher.subscribe(subscriber);

        // Submit an item and close publisher
        String item = "test";
        publisher.submit(item);
        publisher.close();

        // Wait for subscriber to receive item
        subscriber.await();

        // Check that subscriber received the submitted item
        if (!subscriber.received || !item.equals(subscriber.item)) {
            throw new AssertionError("Subscriber did not receive the submitted item correctly");
        }

        // Check that subscriber was completed
        if (!subscriber.completed) {
            throw new AssertionError("Subscriber was not completed after publisher close");
        }
    }

    static class TestSubscriber implements Subscriber<String> {
        volatile boolean received = false;
        volatile boolean completed = false;
        volatile String item = null;
        volatile Subscription subscription = null;

        // Simple synchronization
        private final Object lock = new Object();

        @Override
        public void onSubscribe(Subscription subscription) {
            this.subscription = subscription;
            subscription.request(1);
        }

        @Override
        public void onNext(String item) {
            this.item = item;
            this.received = true;
            // Request no more items
        }

        @Override
        public void onError(Throwable throwable) {
            throw new AssertionError("Unexpected error", throwable);
        }

        @Override
        public void onComplete() {
            this.completed = true;
            synchronized(lock) {
                lock.notifyAll();
            }
        }

        void await() throws InterruptedException {
            synchronized(lock) {
                if (!completed) {
                    lock.wait(5000);
                }
            }
        }
    }
}
