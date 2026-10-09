/*
 * @test
 * @summary Test for SubmissionPublisher.isSubscribed method with null input.
 * @run main SubmissionPublisherWitupTest_P874e61ce3f33
 */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow;

public class SubmissionPublisherWitupTest_P874e61ce3f33 {

    public static void main(String[] args) {
        testIsSubscribedWithNull();
        testIsSubscribedWithExistingSubscriber();
        testIsSubscribedWithNonExistingSubscriber();
    }

    private static void testIsSubscribedWithNull() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        try {
            publisher.isSubscribed(null);
            System.out.println("testIsSubscribedWithNull: Failed - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testIsSubscribedWithNull: Passed");
        } catch (Throwable t) {
            System.out.println("testIsSubscribedWithNull: Failed - Unexpected exception: " + t);
        }
    }

    private static void testIsSubscribedWithExistingSubscriber() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        Flow.Subscriber<Object> subscriber = new Flow.Subscriber<Object>() {
            public void onSubscribe(Flow.Subscription subscription) {}
            public void onNext(Object item) {}
            public void onError(Throwable throwable) {}
            public void onComplete() {}
        };
        publisher.subscribe(subscriber);
        boolean result = publisher.isSubscribed(subscriber);
        if (result) {
            System.out.println("testIsSubscribedWithExistingSubscriber: Passed");
        } else {
            System.out.println("testIsSubscribedWithExistingSubscriber: Failed - Subscriber should be subscribed");
        }
    }

    private static void testIsSubscribedWithNonExistingSubscriber() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        Flow.Subscriber<Object> subscriber = new Flow.Subscriber<Object>() {
            public void onSubscribe(Flow.Subscription subscription) {}
            public void onNext(Object item) {}
            public void onError(Throwable throwable) {}
            public void onComplete() {}
        };
        boolean result = publisher.isSubscribed(subscriber);
        if (!result) {
            System.out.println("testIsSubscribedWithNonExistingSubscriber: Passed");
        } else {
            System.out.println("testIsSubscribedWithNonExistingSubscriber: Failed - Subscriber should not be subscribed");
        }
    }
}
