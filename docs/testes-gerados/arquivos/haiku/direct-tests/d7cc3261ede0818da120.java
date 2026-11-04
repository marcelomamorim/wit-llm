/*
 * @test
 * @summary Checks SubmissionPublisher.isSubscribed for null, empty, subscribed, unrelated and closed publishers
 * @run main SubmissionPublisherWitupTest
 */

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class SubmissionPublisherWitupTest {

    static class NoopSubscriber implements Flow.Subscriber<Integer> {
        public void onSubscribe(Flow.Subscription s) { }
        public void onNext(Integer item) { }
        public void onError(Throwable t) { }
        public void onComplete() { }
    }

    static void check(boolean cond, String msg) {
        if (!cond) throw new AssertionError(msg);
    }

    public static void main(String[] args) throws Exception {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

        boolean npe = false;
        try {
            publisher.isSubscribed(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "isSubscribed(null) must throw NullPointerException");

        NoopSubscriber subscriber = new NoopSubscriber();
        check(!publisher.isSubscribed(subscriber), "new publisher must not report subscriber");

        publisher.subscribe(subscriber);
        check(publisher.isSubscribed(subscriber), "subscribed subscriber must be reported");

        NoopSubscriber other = new NoopSubscriber();
        check(!publisher.isSubscribed(other), "unrelated subscriber must not be reported");

        publisher.close();
        check(!publisher.isSubscribed(subscriber), "closed publisher must report no subscribers");
    }
}
