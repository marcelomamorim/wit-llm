/*
 * @test
 * @summary Test SubmissionPublisher.closeExceptionally behavior
 * @run main SubmissionPublisherWitupTest
 */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

public class SubmissionPublisherWitupTest {

    static class TestSubscriber<T> implements Subscriber<T> {
        volatile Throwable error = null;
        volatile boolean completed = false;
        final AtomicBoolean subscribed = new AtomicBoolean(false);
        Subscription subscription;

        @Override
        public void onSubscribe(Subscription subscription) {
            this.subscription = subscription;
            subscribed.set(true);
            subscription.request(Long.MAX_VALUE);
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
        testCloseExceptionallyNullThrows();
        testCloseExceptionallyDeliversErrorToSubscribers();
    }

    private static void testCloseExceptionallyNullThrows() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        try {
            publisher.closeExceptionally(null);
            throw new AssertionError("Expected NullPointerException when passing null to closeExceptionally");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            publisher.close(); // cleanup
        }
    }

    private static void testCloseExceptionallyDeliversErrorToSubscribers() throws Exception {
        Executor executor = Executors.newSingleThreadExecutor();
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>(executor, 1);
        TestSubscriber<String> subscriber = new TestSubscriber<>();
        publisher.subscribe(subscriber);

        // Wait for subscription to be established
        for (int i = 0; i < 100 && !subscriber.subscribed.get(); i++) {
            Thread.sleep(10);
        }
        if (!subscriber.subscribed.get()) {
            throw new AssertionError("Subscriber did not subscribe in time");
        }

        Throwable testError = new RuntimeException("test error");
        publisher.closeExceptionally(testError);

        // Wait for onError to be called
        for (int i = 0; i < 100 && subscriber.error == null; i++) {
            Thread.sleep(10);
        }

        if (subscriber.error == null) {
            throw new AssertionError("Subscriber did not receive error notification");
        }

        if (subscriber.error != testError) {
            throw new AssertionError("Subscriber received wrong error instance");
        }

        // Subsequent calls to closeExceptionally should have no effect
        publisher.closeExceptionally(new RuntimeException("another error"));

        publisher.close();
        ((java.util.concurrent.ExecutorService) executor).shutdown();
    }
}
