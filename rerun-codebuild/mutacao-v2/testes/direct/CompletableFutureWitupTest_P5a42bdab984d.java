/*
 * @test
 * @summary Test CompletableFuture.failedFuture method behavior
 * @run main CompletableFutureWitupTest_P5a42bdab984d
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest_P5a42bdab984d {
    public static void main(String[] args) {
        testFailedFutureWithNull();
        testFailedFutureWithException();
    }

    static void testFailedFutureWithNull() {
        try {
            CompletableFuture.failedFuture(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    static void testFailedFutureWithException() {
        Throwable ex = new RuntimeException("test");
        CompletableFuture<?> cf = CompletableFuture.failedFuture(ex);
        if (!cf.isCompletedExceptionally()) {
            throw new Error("CompletableFuture should be completed exceptionally");
        }
        try {
            cf.join();
            throw new Error("Expected exception not thrown on join()");
        } catch (RuntimeException e) {
            if (e.getCause() != ex) {
                throw new Error("Cause of exception does not match original", e);
            }
        }
    }
}
