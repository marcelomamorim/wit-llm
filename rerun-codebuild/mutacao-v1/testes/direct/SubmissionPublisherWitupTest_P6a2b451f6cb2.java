/* @test    @summary Test isSubscribed method of SubmissionPublisher for known subscribers    @run main SubmissionPublisherWitupTest_P6a2b451f6cb2 */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscriber;

public class SubmissionPublisherWitupTest_P6a2b451f6cb2 {

    public static void main(String[] args) {
        testIsSubscribedWithExistingSubscriber();
        testIsSubscribedWithNonExistingSubscriber();
        testIsSubscribedWithNullSubscriber();
    }

    static class DummySubscriber implements Subscriber<Object> {
        @Override
        public void onSubscribe(java.util.concurrent.Flow.Subscription subscription) {}
        @Override
        public void onNext(Object item) {}
        @Override
        public void onError(Throwable throwable) {}
        @Override
        public void onComplete() {}
    }

    static void testIsSubscribedWithExistingSubscriber() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        DummySubscriber subscriber = new DummySubscriber();
        // Manually add subscriber to publisher's internal list for testing
        // Note: Since internal list is private, this test assumes access or uses reflection.
        // Given constraints, we simulate by subscribing and then checking.
        publisher.subscribe(subscriber);
        boolean result = publisher.isSubscribed(subscriber);
        if (!result) {
            throw new AssertionError("Expected subscriber to be subscribed.");
        }
    }

    static void testIsSubscribedWithNonExistingSubscriber() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        DummySubscriber subscriber = new DummySubscriber();
        boolean result = publisher.isSubscribed(subscriber);
        if (result) {
            throw new AssertionError("Expected subscriber not to be subscribed.");
        }
    }

    static void testIsSubscribedWithNullSubscriber() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        try {
            publisher.isSubscribed(null);
            throw new AssertionError("Expected NullPointerException for null subscriber.");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
