/*
 * @test
 * @summary SubmissionPublisher.closeExceptionally rejects null, delivers the error to existing subscribers, and ignores a second close
 * @run main SubmissionPublisherWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

public class SubmissionPublisherWitupTest {

    static class RecordingSubscriber implements Flow.Subscriber<Integer> {
        final CountDownLatch errorLatch = new CountDownLatch(1);
        volatile Throwable error;
        volatile int errorCount;

        public void onSubscribe(Flow.Subscription s) {
            s.request(Long.MAX_VALUE);
        }

        public void onNext(Integer item) {
        }

        public void onError(Throwable t) {
            error = t;
            errorCount++;
            errorLatch.countDown();
        }

        public void onComplete() {
        }
    }

    public static void main(String[] args) throws Exception {
        testNullThrowsNPE();
        testErrorDeliveredToSubscriber();
        testSecondCloseDoesNotReplaceError();
    }

    static void testNullThrowsNPE() {
        SubmissionPublisher<Integer> p = new SubmissionPublisher<>();
        try {
            p.closeExceptionally(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null error");
    }

    static void testErrorDeliveredToSubscriber() throws Exception {
        Executor direct = Runnable::run;
        SubmissionPublisher<Integer> p = new SubmissionPublisher<>(direct, 16);
        RecordingSubscriber sub = new RecordingSubscriber();
        p.subscribe(sub);
        RuntimeException err = new RuntimeException("boom");
        p.closeExceptionally(err);
        if (!sub.errorLatch.await(10, TimeUnit.SECONDS)) {
            throw new RuntimeException("onError not delivered");
        }
        if (sub.error != err) {
            throw new RuntimeException("unexpected error instance: " + sub.error);
        }
        if (sub.errorCount != 1) {
            throw new RuntimeException("onError called " + sub.errorCount + " times");
        }
    }

    static void testSecondCloseDoesNotReplaceError() throws Exception {
        Executor direct = Runnable::run;
        SubmissionPublisher<Integer> p = new SubmissionPublisher<>(direct, 16);
        RecordingSubscriber sub = new RecordingSubscriber();
        p.subscribe(sub);
        RuntimeException first = new RuntimeException("first");
        RuntimeException second = new RuntimeException("second");
        p.closeExceptionally(first);
        p.closeExceptionally(second);
        if (!sub.errorLatch.await(10, TimeUnit.SECONDS)) {
            throw new RuntimeException("onError not delivered");
        }
        if (sub.error != first) {
            throw new RuntimeException("second close replaced the first error");
        }
        if (sub.errorCount != 1) {
            throw new RuntimeException("onError called " + sub.errorCount + " times");
        }
    }
}
