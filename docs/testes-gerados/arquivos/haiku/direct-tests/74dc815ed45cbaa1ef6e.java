/*
 * @test
 * @summary Checks observable behavior of SubmissionPublisher.subscribe: null rejection, duplicate subscribe error, and subscribe after close.
 * @run main SubmissionPublisherWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class SubmissionPublisherWitupTest {

    static final class RecordingSubscriber implements Flow.Subscriber<Integer> {
        final List<String> events = new ArrayList<>();

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            events.add("subscribe");
        }

        @Override
        public void onNext(Integer item) {
            events.add("next:" + item);
        }

        @Override
        public void onError(Throwable throwable) {
            events.add("error:" + throwable.getClass().getName() + ":" + throwable.getMessage());
        }

        @Override
        public void onComplete() {
            events.add("complete");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static final Executor DIRECT = Runnable::run;

    static void testNullSubscriberThrowsNpe() {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>(DIRECT, 8);
        boolean thrown = false;
        try {
            publisher.subscribe(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "subscribe(null) must throw NullPointerException");
    }

    static void testDuplicateSubscribeReportsError() {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>(DIRECT, 8);
        RecordingSubscriber subscriber = new RecordingSubscriber();
        publisher.subscribe(subscriber);
        publisher.subscribe(subscriber);
        check(subscriber.events.contains(
                "error:java.lang.IllegalStateException:Duplicate subscribe"),
                "duplicate subscribe must deliver IllegalStateException(\"Duplicate subscribe\"), got "
                        + subscriber.events);
    }

    static void testSubscribeAfterCloseCompletes() {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>(DIRECT, 8);
        publisher.close();
        RecordingSubscriber subscriber = new RecordingSubscriber();
        publisher.subscribe(subscriber);
        check(subscriber.events.contains("complete"),
                "subscribing to a closed publisher must deliver onComplete, got " + subscriber.events);
    }

    public static void main(String[] args) {
        testNullSubscriberThrowsNpe();
        testDuplicateSubscribeReportsError();
        testSubscribeAfterCloseCompletes();
    }
}
