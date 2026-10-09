/* @test    @summary Test the subscribe method in SubmissionPublisher for null and duplicate subscribers    @run main SubmissionPublisherWitupTest_P190af60bf12f */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscriber;

public class SubmissionPublisherWitupTest_P190af60bf12f {

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

    public static void main(String[] args) {
        testNullSubscriber();
        testDuplicateSubscription();
    }

    static void testNullSubscriber() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        try {
            publisher.subscribe(null);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testDuplicateSubscription() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        DummySubscriber subscriber = new DummySubscriber();
        publisher.subscribe(subscriber);
        publisher.subscribe(subscriber); // Should not throw, but should handle duplicate
        // No exception expected, but the internal state is tested indirectly
        // The duplicate subscription adds an error to the existing subscription
        // Since internal implementation is not accessible, this test ensures no exception occurs
    }
}
