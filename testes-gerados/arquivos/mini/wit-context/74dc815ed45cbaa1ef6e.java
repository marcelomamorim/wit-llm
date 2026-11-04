/*
 * @test
 * @summary Test subscribe method of SubmissionPublisher for null subscriber and duplicate subscription
 * @run main SubmissionPublisherWitupTest
 */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SubmissionPublisherWitupTest {

    static class TestSubscriber<T> implements Subscriber<T> {
        volatile boolean subscribed = false;
        volatile boolean completed = false;
        volatile Throwable error = null;
        volatile Subscription subscription = null;

        @Override
        public void onSubscribe(Subscription subscription) {
            this.subscription = subscription;
            subscribed = true;
        }

        @Override
        public void onNext(T item) {
            // no-op
        }

        @Override
        public void onError(Throwable throwable) {
            this.error = throwable;
        }

        @Override
        public void onComplete() {
            completed = true;
        }
    }

    public static void main(String[] args) throws Exception {
        testSubscribeNullSubscriber();
        testSubscribeDuplicateSubscriber();
    }

    private static void testSubscribeNullSubscriber() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        try {
            publisher.subscribe(null);
            throw new AssertionError("Expected NullPointerException for null subscriber");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            publisher.close();
        }
    }

    private static void testSubscribeDuplicateSubscriber() throws Exception {
        Executor executor = Executors.newSingleThreadExecutor();
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>(executor, 16);
        try {
            TestSubscriber<Object> subscriber = new TestSubscriber<>();
            publisher.subscribe(subscriber);

            // Wait for onSubscribe to be called
            waitForCondition(() -> subscriber.subscribed, 1000);

            TestSubscriber<Object> duplicateSubscriber = new TestSubscriber<Object>() {
                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                    if (!(t instanceof IllegalStateException)) {
                        throw new AssertionError("Expected IllegalStateException for duplicate subscribe");
                    }
                }
            };

            publisher.subscribe(duplicateSubscriber);

            // Wait briefly to allow onError to be called
            Thread.sleep(100);

            if (!(duplicateSubscriber.error instanceof IllegalStateException)) {
                throw new AssertionError("Duplicate subscriber did not receive IllegalStateException");
            }

        } finally {
            publisher.close();
            if (executor instanceof java.util.concurrent.ExecutorService) {
                ((java.util.concurrent.ExecutorService) executor).shutdownNow();
            }
        }
    }

    private static void waitForCondition(Check condition, long timeoutMillis) throws InterruptedException {
        long start = System.currentTimeMillis();
        while (!condition.check()) {
            if (System.currentTimeMillis() - start > timeoutMillis) {
                throw new AssertionError("Timeout waiting for condition");
            }
            Thread.sleep(10);
        }
    }

    private interface Check {
        boolean check();
    }
}
