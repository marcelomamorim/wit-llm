/* @test    @summary Test subscribe method of SubmissionPublisher for null and duplicate subscribers    @run main SubmissionPublisherWitupTest_P52195cf42f5e */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscriber;

public class SubmissionPublisherWitupTest_P52195cf42f5e {

    static class DummySubscriber<T> implements Subscriber<T> {
        @Override
        public void onSubscribe(java.util.concurrent.Flow.Subscription subscription) {}
        @Override
        public void onNext(T item) {}
        @Override
        public void onError(Throwable throwable) {}
        @Override
        public void onComplete() {}
    }

    public static void main(String[] args) {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        try {
            publisher.subscribe(null);
            throw new AssertionError("Expected NullPointerException for null subscriber");
        } catch (NullPointerException e) {
            // expected
        }

        DummySubscriber<Object> subscriber1 = new DummySubscriber<>();
        DummySubscriber<Object> subscriber2 = new DummySubscriber<>();

        // First subscription should succeed
        publisher.subscribe(subscriber1);
        // Duplicate subscription should throw an exception or handle internally
        // Since the actual method does not throw, we test that no exception is thrown here
        try {
            publisher.subscribe(subscriber1);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception on duplicate subscribe", e);
        }

        // Subscribe a different subscriber
        publisher.subscribe(subscriber2);
    }
}
