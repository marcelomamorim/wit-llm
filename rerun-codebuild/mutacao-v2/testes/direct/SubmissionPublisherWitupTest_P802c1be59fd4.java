/*
 * @test
 * @summary Test SubmissionPublisher.subscribe behavior including duplicate and null subscriber
 * @run main SubmissionPublisherWitupTest_P802c1be59fd4
 */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class SubmissionPublisherWitupTest_P802c1be59fd4 {

    static class TestSubscriber<T> implements Subscriber<T> {
        volatile boolean subscribed = false;
        volatile boolean completed = false;
        volatile Throwable error = null;
        volatile Subscription subscription = null;

        public void onSubscribe(Subscription s) {
            subscribed = true;
            subscription = s;
        }

        public void onNext(T item) {
            // no-op
        }

        public void onError(Throwable t) {
            error = t;
        }

        public void onComplete() {
            completed = true;
        }
    }

    public static void main(String[] args) throws Exception {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();

        // Test null subscriber throws NullPointerException
        try {
            publisher.subscribe(null);
            throw new Error("Expected NullPointerException for null subscriber");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test normal subscribe
        TestSubscriber<Object> sub1 = new TestSubscriber<>();
        publisher.subscribe(sub1);
        if (!sub1.subscribed) {
            throw new Error("Subscriber should be subscribed after subscribe call");
        }

        // Test duplicate subscribe triggers onError with IllegalStateException
        TestSubscriber<Object> sub2 = new TestSubscriber<>();
        publisher.subscribe(sub2);
        // subscribe same subscriber again
        publisher.subscribe(sub2);
        // The second subscribe of sub2 should cause onError with IllegalStateException
        if (sub2.error == null || !(sub2.error instanceof IllegalStateException)) {
            throw new Error("Duplicate subscribe should cause IllegalStateException on subscriber");
        }

        // Test that different subscriber can subscribe without error
        TestSubscriber<Object> sub3 = new TestSubscriber<>();
        publisher.subscribe(sub3);
        if (!sub3.subscribed) {
            throw new Error("Different subscriber should be subscribed successfully");
        }

        publisher.close();
    }
}
