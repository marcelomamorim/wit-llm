/*
 * @test
 * @summary Test SubmissionPublisher onError method behavior
 * @run main SubmissionPublisherWitupTest
 */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SubmissionPublisherWitupTest {
    public static void main(String[] args) throws Exception {
        testOnErrorCompletesExceptionally();
    }

    static void testOnErrorCompletesExceptionally() throws Exception {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        Throwable testEx = new RuntimeException("test exception");

        // Call onError with a test exception
        publisher.onError(testEx);

        // The SubmissionPublisher has a CompletableFuture status field (not public),
        // but we can observe the effect by submitting and checking completion.
        // Since status is private, we test observable behavior: after onError,
        // submit should throw IllegalStateException because publisher is closed exceptionally.

        boolean threw = false;
        try {
            publisher.submit(new Object());
        } catch (IllegalStateException ise) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected IllegalStateException after onError");
        }

        // Also test that close() does not throw after onError
        publisher.close();
    }
}
