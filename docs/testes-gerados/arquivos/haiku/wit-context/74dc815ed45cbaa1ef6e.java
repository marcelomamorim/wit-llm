/*
 * @test
 * @summary WITUP-derived checks for SubmissionPublisher.subscribe null and duplicate subscription handling
 * @run main SubmissionPublisherWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

public class SubmissionPublisherWitupTest {

    static final class RecordingSubscriber implements Flow.Subscriber<String> {
        final CountDownLatch errorLatch = new CountDownLatch(1);
        volatile Throwable error;

        @Override
        public void onSubscribe(Flow.Subscription s) {
            s.request(Long.MAX_VALUE);
        }

        @Override
        public void onNext(String item) {
        }

        @Override
        public void onError(Throwable t) {
            error = t;
            errorLatch.countDown();
        }

        @Override
        public void onComplete() {
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new AssertionError(msg);
        }
    }

    static void testNullSubscriberThrowsNPE() {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        try {
            publisher.subscribe(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            publisher.close();
        }
    }

    static void testDuplicateSubscribeSignalsIllegalState() throws InterruptedException {
        Executor direct = Runnable::run;
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>(direct, 16);
        try {
            RecordingSubscriber sub = new RecordingSubscriber();
            publisher.subscribe(sub);
            publisher.subscribe(sub);
            check(sub.errorLatch.await(10, TimeUnit.SECONDS),
                  "onError not delivered for duplicate subscribe");
            check(sub.error instanceof IllegalStateException,
                  "expected IllegalStateException but got " + sub.error);
            check("Duplicate subscribe".equals(sub.error.getMessage()),
                  "unexpected message: " + sub.error.getMessage());
        } finally {
            publisher.close();
        }
    }

    public static void main(String[] args) throws Exception {
        testNullSubscriberThrowsNPE();
        testDuplicateSubscribeSignalsIllegalState();
    }
}
