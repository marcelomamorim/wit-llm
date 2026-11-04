/*
 * @test
 * @summary Verifies that SubmissionPublisher.onError delivers the exact throwable to subscribers and rejects null
 * @run main SubmissionPublisherWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SubmissionPublisherWitupTest {

    static class RecordingSubscriber implements Flow.Subscriber<Integer> {
        final CountDownLatch errorLatch = new CountDownLatch(1);
        final AtomicReference<Throwable> error = new AtomicReference<>();

        public void onSubscribe(Flow.Subscription s) {
            s.request(Long.MAX_VALUE);
        }

        public void onNext(Integer item) {
        }

        public void onError(Throwable t) {
            error.set(t);
            errorLatch.countDown();
        }

        public void onComplete() {
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            // onError must propagate the same throwable instance to a subscriber
            SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>(executor, 16);
            RecordingSubscriber subscriber = new RecordingSubscriber();
            publisher.subscribe(subscriber);
            RuntimeException boom = new RuntimeException("boom");
            publisher.onError(boom);
            if (!subscriber.errorLatch.await(10, TimeUnit.SECONDS)) {
                throw new RuntimeException("subscriber did not receive onError");
            }
            if (subscriber.error.get() != boom) {
                throw new RuntimeException("unexpected throwable delivered: " + subscriber.error.get());
            }

            // onError(null) must be rejected, since CompletableFuture.completeExceptionally rejects null
            SubmissionPublisher<Integer> nullPublisher = new SubmissionPublisher<>(executor, 16);
            boolean npe = false;
            try {
                nullPublisher.onError(null);
            } catch (NullPointerException expected) {
                npe = true;
            }
            if (!npe) {
                throw new RuntimeException("onError(null) did not throw NullPointerException");
            }
        } finally {
            executor.shutdown();
        }
    }
}
