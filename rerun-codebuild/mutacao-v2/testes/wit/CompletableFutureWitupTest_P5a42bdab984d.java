/*
 * @test
 * @summary Test CompletableFuture.failedFuture method for correct exception and result
 * @run main CompletableFutureWitupTest_P5a42bdab984d
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest_P5a42bdab984d {
    public static void main(String[] args) {
        testFailedFutureWithNull();
        testFailedFutureWithThrowable();
        System.out.println("All tests passed.");
    }

    private static void testFailedFutureWithNull() {
        try {
            CompletableFuture.failedFuture(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testFailedFutureWithThrowable() {
        Throwable ex = new RuntimeException("test exception");
        CompletableFuture<?> cf = CompletableFuture.failedFuture(ex);
        if (cf == null) {
            throw new AssertionError("failedFuture returned null CompletableFuture");
        }
        // The CompletableFuture should be completed exceptionally with the given Throwable
        if (!cf.isCompletedExceptionally()) {
            throw new AssertionError("CompletableFuture is not completed exceptionally");
        }
        // Attempt to get the result should throw the original exception wrapped in CompletionException
        try {
            cf.join();
            throw new AssertionError("Expected exception when joining failedFuture");
        } catch (RuntimeException e) {
            Throwable cause = e.getCause();
            if (cause != ex) {
                throw new AssertionError("Exception cause is not the original Throwable");
            }
        }
    }
}
