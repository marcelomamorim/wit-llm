/* @test    @summary Test isSubscribed method of SubmissionPublisher for subscriber presence and absence    @run main SubmissionPublisherWitupTest_P874e61ce3f33 */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscriber;

public class SubmissionPublisherWitupTest_P874e61ce3f33 {

    public static void main(String[] args) {
        testSubscriberPresent();
        testSubscriberAbsent();
    }

    static void testSubscriberPresent() {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        DummySubscriber subscriber = new DummySubscriber();
        publisher.subscribe(subscriber);
        boolean result = publisher.isSubscribed(subscriber);
        if (!result) {
            throw new AssertionError("Expected subscriber to be subscribed.");
        }
    }

    static void testSubscriberAbsent() {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        DummySubscriber subscriber = new DummySubscriber();
        // Not subscribing the subscriber
        boolean result = publisher.isSubscribed(subscriber);
        if (result) {
            throw new AssertionError("Expected subscriber not to be subscribed.");
        }
    }

    static class DummySubscriber implements Subscriber<Integer> {
        @Override
        public void onSubscribe(java.util.concurrent.Flow.Subscription subscription) {
            // no-op
        }

        @Override
        public void onNext(Integer item) {
            // no-op
        }

        @Override
        public void onError(Throwable throwable) {
            // no-op
        }

        @Override
        public void onComplete() {
            // no-op
        }
    }
}
