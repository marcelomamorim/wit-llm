/*
 * @test
 * @summary Witup-derived checks for SubmissionPublisher.isSubscribed null handling and subscription state
 * @run main SubmissionPublisherWitupTest
 */

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class SubmissionPublisherWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static class NoopSubscriber implements Flow.Subscriber<Integer> {
        public void onSubscribe(Flow.Subscription subscription) { }
        public void onNext(Integer item) { }
        public void onError(Throwable throwable) { }
        public void onComplete() { }
    }

    public static void main(String[] args) {
        testNullSubscriberThrowsNpe();
        testUnsubscribedReturnsFalse();
        testSubscribedReturnsTrue();
        testOtherSubscriberReturnsFalse();
        testClosedPublisherReturnsFalse();
    }

    static void testNullSubscriberThrowsNpe() {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        try {
            publisher.isSubscribed(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
        } finally {
            publisher.close();
        }
    }

    static void testUnsubscribedReturnsFalse() {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        try {
            Flow.Subscriber<Integer> s = new NoopSubscriber();
            check(!publisher.isSubscribed(s), "new subscriber should not be subscribed");
        } finally {
            publisher.close();
        }
    }

    static void testSubscribedReturnsTrue() {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        try {
            Flow.Subscriber<Integer> s = new NoopSubscriber();
            publisher.subscribe(s);
            check(publisher.isSubscribed(s), "subscribed subscriber should be reported as subscribed");
        } finally {
            publisher.close();
        }
    }

    static void testOtherSubscriberReturnsFalse() {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        try {
            Flow.Subscriber<Integer> s1 = new NoopSubscriber();
            Flow.Subscriber<Integer> s2 = new NoopSubscriber();
            publisher.subscribe(s1);
            check(publisher.isSubscribed(s1), "s1 should be subscribed");
            check(!publisher.isSubscribed(s2), "s2 should not be subscribed");
        } finally {
            publisher.close();
        }
    }

    static void testClosedPublisherReturnsFalse() {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        Flow.Subscriber<Integer> s = new NoopSubscriber();
        publisher.subscribe(s);
        publisher.close();
        check(!publisher.isSubscribed(s), "closed publisher should report false");
    }
}
