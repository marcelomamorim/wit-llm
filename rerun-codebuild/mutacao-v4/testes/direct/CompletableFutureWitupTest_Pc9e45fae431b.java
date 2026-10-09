/* @test
   @summary Verifies that failedStage returns a CompletionStage that completes exceptionally with the provided Throwable.
   @run main CompletableFutureWitupTest_Pc9e45fae431b
 */
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.Objects;

public class CompletableFutureWitupTest_Pc9e45fae431b {
    public static void main(String[] args) throws Exception {
        testFailedStageWithNull();
        testFailedStageWithException();
    }

    private static void testFailedStageWithNull() {
        try {
            CompletableFuture.failedStage(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testFailedStageWithException() throws Exception {
        Throwable ex = new RuntimeException("Test exception");
        CompletableFuture<Throwable> stage = (CompletableFuture<Throwable>) CompletableFuture.failedStage(ex);
        if (!stage.isDone()) {
            throw new AssertionError("Stage should be completed");
        }
        if (!stage.isCompletedExceptionally()) {
            throw new AssertionError("Stage should be completed exceptionally");
        }
        try {
            stage.get();
            throw new AssertionError("Expected ExecutionException");
        } catch (ExecutionException e) {
            if (e.getCause() != ex) {
                throw new AssertionError("Cause mismatch", e);
            }
        }
    }
}
