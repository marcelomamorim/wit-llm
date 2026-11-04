/*
 * @test
 * @summary WITUP-generated checks for SubmissionPublisher.closeExceptionally(Throwable)
 * @run main SubmissionPublisherWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SubmissionPublisherWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static class RecordingSubscriber implements Flow.Subscriber<Integer> {
        final CountDownLatch errorLatch = new CountDownLatch(1);
        final AtomicReference<Throwable> error = new AtomicReference<>();
        volatile boolean completed;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            subscription.request(Long.MAX_VALUE);
        }

        @Override
        public void onNext(Integer item) {
        }

        @Override
        public void onError(Throwable throwable) {
            error.set(throwable);
            errorLatch.countDown();
        }

        @Override
        public void onComplete() {
            completed = true;
        }
    }

    static void testNullErrorThrowsNPE() {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        boolean thrown = false;
        try {
            publisher.closeExceptionally(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "closeExceptionally(null) must throw NullPointerException");
    }

    static void testNullErrorThrowsNPEAfterClose() {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        publisher.closeExceptionally(new RuntimeException("initial"));
        boolean thrown = false;
        try {
            publisher.closeExceptionally(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "closeExceptionally(null) must throw NullPointerException even after close");
    }

    static void testErrorDeliveredAndFirstErrorRetained() throws InterruptedException {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        RecordingSubscriber subscriber = new RecordingSubscriber();
        publisher.subscribe(subscriber);
        RuntimeException first = new RuntimeException("first");
        publisher.closeExceptionally(first);
        publisher.closeExceptionally(new IllegalStateException("second"));
        check(subscriber.errorLatch.await(10, TimeUnit.SECONDS),
              "subscriber must receive onError");
        check(subscriber.error.get() == first,
              "subscriber must receive the first error passed to closeExceptionally");
        check(!subscriber.completed, "onComplete must not be signaled");
    }

    public static void main(String[] args) throws Exception {
        testNullErrorThrowsNPE();
        testNullErrorThrowsNPEAfterClose();
        testErrorDeliveredAndFirstErrorRetained();
    }
}
