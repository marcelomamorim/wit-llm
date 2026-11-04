/* @test    @summary Test SubmissionPublisher.onError(Throwable ex) method for exception handling.    @run main SubmissionPublisherWitupTest */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class SubmissionPublisherWitupTest {

    public static void main(String[] args) {
        testOnErrorWithNullThrowable();
        testOnErrorWithRuntimeException();
    }

    static void testOnErrorWithNullThrowable() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        try {
            publisher.onError(null);
        } catch (NullPointerException e) {
            System.out.println("Passed: onError(null) throws NullPointerException");
            return;
        }
        System.out.println("Failed: onError(null) did not throw NullPointerException");
    }

    static void testOnErrorWithRuntimeException() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        RuntimeException ex = new RuntimeException("Test exception");
        publisher.onError(ex);
        // Since status.completeExceptionally(ex) is called, we can verify by subscribing and checking for completion
        final boolean[] completed = {false};
        publisher.subscribe(new java.util.concurrent.Flow.Subscriber<Object>() {
            @Override
            public void onSubscribe(java.util.concurrent.Flow.Subscription subscription) {
                subscription.request(Long.MAX_VALUE);
            }
            @Override
            public void onNext(Object item) {
                // no-op
            }
            @Override
            public void onError(Throwable t) {
                if (t == ex) {
                    System.out.println("Passed: onError propagates exception to subscriber");
                } else {
                    System.out.println("Failed: onError propagates wrong exception");
                }
                completed[0] = true;
            }
            @Override
            public void onComplete() {
                System.out.println("Failed: onComplete called after onError");
                completed[0] = true;
            }
        });
        // Wait briefly to allow callback
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // ignore
        }
        if (!completed[0]) {
            System.out.println("Failed: onError did not trigger onError in subscriber");
        }
    }
}
