/*
 * @test
 * @summary Test SubmissionPublisher.closeExceptionally behavior
 * @run main SubmissionPublisherWitupTest_P6567d6df33b0
 */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class SubmissionPublisherWitupTest_P6567d6df33b0 {

    static class TestSubscriber<T> implements Subscriber<T> {
        volatile Throwable error = null;
        volatile boolean completed = false;
        volatile boolean subscribed = false;
        volatile boolean onSubscribeCalled = false;
        volatile boolean onNextCalled = false;
        volatile boolean onCompleteCalled = false;
        volatile boolean onErrorCalled = false;
        volatile Subscription subscription = null;

        public void onSubscribe(Subscription s) {
            onSubscribeCalled = true;
            subscription = s;
            s.request(1);
        }

        public void onNext(T item) {
            onNextCalled = true;
        }

        public void onError(Throwable t) {
            error = t;
            onErrorCalled = true;
        }

        public void onComplete() {
            completed = true;
            onCompleteCalled = true;
        }
    }

    public static void main(String[] args) throws Exception {
        testCloseExceptionallyNullError();
        testCloseExceptionallyDeliversErrorToSubscribers();
    }

    static void testCloseExceptionallyNullError() {
        SubmissionPublisher<Object> pub = new SubmissionPublisher<>();
        try {
            pub.closeExceptionally(null);
            throw new AssertionError("Expected NullPointerException for null error");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            pub.close();
        }
    }

    static void testCloseExceptionallyDeliversErrorToSubscribers() throws Exception {
        Executor exec = Executors.newSingleThreadExecutor();
        SubmissionPublisher<Object> pub = new SubmissionPublisher<>(exec, 1);
        TestSubscriber<Object> sub = new TestSubscriber<>();
        pub.subscribe(sub);

        // Wait for onSubscribe to be called
        int waitCount = 0;
        while (!sub.onSubscribeCalled && waitCount < 1000) {
            Thread.sleep(1);
            waitCount++;
        }
        if (!sub.onSubscribeCalled) {
            throw new AssertionError("Subscriber did not receive onSubscribe in time");
        }

        Throwable testError = new RuntimeException("test error");
        pub.closeExceptionally(testError);

        // Wait for onError to be called
        waitCount = 0;
        while (!sub.onErrorCalled && waitCount < 1000) {
            Thread.sleep(1);
            waitCount++;
        }

        if (!sub.onErrorCalled) {
            throw new AssertionError("Subscriber did not receive onError in time");
        }

        if (sub.error != testError) {
            throw new AssertionError("Subscriber received wrong error instance");
        }

        exec.shutdown();
    }
}
