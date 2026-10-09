/* @test    @summary Test CompletableFuture.failedFuture with null and non-null Throwable    @run main CompletableFutureWitupTest_Pa81d82c36fd4 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest_Pa81d82c36fd4 {

    public static void main(String[] args) {
        testFailedFutureWithNull();
        testFailedFutureWithException();
    }

    static void testFailedFutureWithNull() {
        try {
            CompletableFuture.failedFuture(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }

    static void testFailedFutureWithException() {
        Throwable ex = new RuntimeException("Test exception");
        CompletableFuture<?> cf = CompletableFuture.failedFuture(ex);
        if (cf == null) {
            throw new AssertionError("failedFuture returned null");
        }
        if (!cf.isDone()) {
            throw new AssertionError("CompletableFuture not completed");
        }
        try {
            cf.get();
            throw new AssertionError("Expected ExecutionException not thrown");
        } catch (java.util.concurrent.ExecutionException e) {
            if (e.getCause() != ex) {
                throw new AssertionError("Cause mismatch: expected " + ex + " but got " + e.getCause());
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }
}
