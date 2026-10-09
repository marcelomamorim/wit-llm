/* @test    @summary Test for CompletableFuture.failedStage(Throwable ex) method
 * @run main CompletableFutureWitupTest_P10b1f803c4ee
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest_P10b1f803c4ee {

    public static void main(String[] args) {
        testFailedStageWithNull();
        testFailedStageWithException();
    }

    private static void testFailedStageWithNull() {
        try {
            CompletableFuture.failedStage(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }

    private static void testFailedStageWithException() {
        Throwable ex = new RuntimeException("Test exception");
        CompletionStage<?> stage = CompletableFuture.failedStage(ex);
        if (stage == null) {
            throw new AssertionError("failedStage returned null");
        }
        if (!(stage instanceof CompletableFuture)) {
            throw new AssertionError("Returned stage is not a CompletableFuture");
        }
        CompletableFuture<?> cf = (CompletableFuture<?>) stage;
        if (!cf.isCompletedExceptionally()) {
            throw new AssertionError("CompletableFuture is not completed exceptionally");
        }
        try {
            cf.get();
            throw new AssertionError("Expected exception not thrown by get()");
        } catch (java.util.concurrent.ExecutionException e) {
            if (e.getCause() != ex) {
                throw new AssertionError("Cause of ExecutionException does not match original exception");
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }
}
