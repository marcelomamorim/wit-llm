/*
 * @test
 * @summary Test SubmissionPublisher.isSubscribed behavior including null argument
 * @run main SubmissionPublisherWitupTest
 */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscriber;

public class SubmissionPublisherWitupTest {

    static class DummySubscriber implements Subscriber<Object> {
        public void onSubscribe(java.util.concurrent.Flow.Subscription subscription) {}
        public void onNext(Object item) {}
        public void onError(Throwable throwable) {}
        public void onComplete() {}
    }

    public static void main(String[] args) {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();

        // Test that isSubscribed throws NullPointerException if subscriber is null
        try {
            publisher.isSubscribed(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test that isSubscribed returns false for a subscriber not subscribed
        DummySubscriber sub = new DummySubscriber();
        boolean result = publisher.isSubscribed(sub);
        if (result) {
            throw new Error("isSubscribed returned true for non-subscribed subscriber");
        }

        // Test that isSubscribed returns true for a subscribed subscriber
        publisher.subscribe(sub);
        boolean subscribed = publisher.isSubscribed(sub);
        if (!subscribed) {
            throw new Error("isSubscribed returned false for subscribed subscriber");
        }

        // Test that isSubscribed returns false after closing publisher
        publisher.close();
        boolean afterClose = publisher.isSubscribed(sub);
        if (afterClose) {
            throw new Error("isSubscribed returned true after publisher closed");
        }

        System.out.println("SubmissionPublisherWitupTest passed");
    }
}
