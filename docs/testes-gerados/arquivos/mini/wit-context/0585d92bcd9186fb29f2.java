/*
 * @test
 * @summary Test SubmissionPublisher() constructor basic behavior
 * @run main SubmissionPublisherWitupTest
 */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class SubmissionPublisherWitupTest {
    public static void main(String[] args) throws Exception {
        // Test default constructor creates a non-null instance
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        if (publisher == null) {
            throw new AssertionError("SubmissionPublisher() returned null");
        }

        // Test that publisher accepts a subscriber and calls onSubscribe
        TestSubscriber<Object> subscriber = new TestSubscriber<>();
        publisher.subscribe(subscriber);

        // Give some time for async subscription
        Thread.sleep(100);

        if (!subscriber.subscribed) {
            throw new AssertionError("Subscriber was not subscribed");
        }

        // Test that submit with null throws NullPointerException
        boolean npeThrown = false;
        try {
            publisher.submit(null);
        } catch (NullPointerException npe) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("submit(null) did not throw NullPointerException");
        }

        // Close publisher and verify no exceptions
        publisher.close();

        // After close, submit returns false
        boolean submitResult = publisher.submit("test");
        if (submitResult) {
            throw new AssertionError("submit() after close should return false");
        }
    }

    static class TestSubscriber<T> implements Subscriber<T> {
        volatile boolean subscribed = false;
        volatile boolean completed = false;
        volatile Throwable error = null;

        @Override
        public void onSubscribe(Subscription subscription) {
            subscribed = true;
            subscription.request(1);
        }

        @Override
        public void onNext(T item) {
            // no-op
        }

        @Override
        public void onError(Throwable throwable) {
            error = throwable;
        }

        @Override
        public void onComplete() {
            completed = true;
        }
    }
}
