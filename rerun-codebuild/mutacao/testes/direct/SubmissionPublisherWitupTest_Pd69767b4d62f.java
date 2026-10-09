/*
 * @test
 * @summary Test isSubscribed method of SubmissionPublisher for various subscribers
 * @run main SubmissionPublisherWitupTest_Pd69767b4d62f
 */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscriber;

public class SubmissionPublisherWitupTest_Pd69767b4d62f {

    public static void main(String[] args) {
        testIsSubscribedWithSubscribedSubscriber();
        testIsSubscribedWithUnsubscribedSubscriber();
        testIsSubscribedWithNullSubscriber();
    }

    static void testIsSubscribedWithSubscribedSubscriber() {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        Subscriber<String> subscriber = new Subscriber<>() {
            @Override public void onSubscribe(java.util.concurrent.Flow.Subscription subscription) {}
            @Override public void onNext(String item) {}
            @Override public void onError(Throwable throwable) {}
            @Override public void onComplete() {}
        };
        publisher.subscribe(subscriber);
        boolean result = publisher.isSubscribed(subscriber);
        if (!result) {
            throw new AssertionError("Expected subscriber to be subscribed");
        }
    }

    static void testIsSubscribedWithUnsubscribedSubscriber() {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        Subscriber<String> subscriber = new Subscriber<>() {
            @Override public void onSubscribe(java.util.concurrent.Flow.Subscription subscription) {}
            @Override public void onNext(String item) {}
            @Override public void onError(Throwable throwable) {}
            @Override public void onComplete() {}
        };
        // Not subscribing the subscriber
        boolean result = publisher.isSubscribed(subscriber);
        if (result) {
            throw new AssertionError("Expected subscriber to be not subscribed");
        }
    }

    static void testIsSubscribedWithNullSubscriber() {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        try {
            publisher.isSubscribed(null);
            throw new AssertionError("Expected NullPointerException for null subscriber");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
