/*
 * @test
 * @summary Test SubmissionPublisher.isSubscribed method behavior
 * @run main SubmissionPublisherWitupTest
 */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class SubmissionPublisherWitupTest {

    static class TestSubscriber<T> implements Subscriber<T> {
        volatile Subscription subscription;
        volatile boolean completed = false;
        volatile Throwable error = null;

        @Override
        public void onSubscribe(Subscription subscription) {
            this.subscription = subscription;
            subscription.request(1);
        }

        @Override
        public void onNext(T item) {
            // no-op
        }

        @Override
        public void onError(Throwable throwable) {
            this.error = throwable;
        }

        @Override
        public void onComplete() {
            this.completed = true;
        }
    }

    public static void main(String[] args) throws Exception {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        // Test 1: Null subscriber should throw NullPointerException
        try {
            publisher.isSubscribed(null);
            throw new Error("Expected NullPointerException for null subscriber");
        } catch (NullPointerException expected) {
        }

        // Test 2: Subscriber not subscribed returns false
        TestSubscriber<String> sub1 = new TestSubscriber<>();
        boolean result = publisher.isSubscribed(sub1);
        if (result) throw new Error("Expected false for non-subscribed subscriber");

        // Test 3: Subscriber subscribed returns true
        publisher.subscribe(sub1);
        // Wait briefly to ensure subscription is processed
        Thread.sleep(100);
        result = publisher.isSubscribed(sub1);
        if (!result) throw new Error("Expected true for subscribed subscriber");

        // Test 4: After close, isSubscribed returns false
        publisher.close();
        // Wait briefly to ensure close processed
        Thread.sleep(100);
        result = publisher.isSubscribed(sub1);
        if (result) throw new Error("Expected false after publisher closed");

        // Test 5: Multiple subscribers, check isSubscribed for each
        SubmissionPublisher<String> pub2 = new SubmissionPublisher<>();
        TestSubscriber<String> subA = new TestSubscriber<>();
        TestSubscriber<String> subB = new TestSubscriber<>();
        pub2.subscribe(subA);
        pub2.subscribe(subB);
        Thread.sleep(100);
        if (!pub2.isSubscribed(subA)) throw new Error("Expected subA to be subscribed");
        if (!pub2.isSubscribed(subB)) throw new Error("Expected subB to be subscribed");

        // Test 6: isSubscribed returns false for unknown subscriber
        TestSubscriber<String> subUnknown = new TestSubscriber<>();
        if (pub2.isSubscribed(subUnknown)) throw new Error("Expected false for unknown subscriber");

        pub2.close();

        System.out.println("All tests passed.");
    }
}
