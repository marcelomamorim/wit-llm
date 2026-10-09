/* @test    @summary Test the failedFuture method of CompletableFuture for null argument handling    @run main CompletableFutureWitupTest_Pc96e48caabd2 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest_Pc96e48caabd2 {
    public static void main(String[] args) {
        testFailedFutureWithNull();
        testFailedFutureWithValidThrowable();
    }

    private static void testFailedFutureWithNull() {
        try {
            CompletableFuture.failedFuture(null);
            System.err.println("Failed: expected NullPointerException was not thrown");
        } catch (NullPointerException expected) {
            // Test passes as exception is expected
        } catch (Throwable t) {
            System.err.println("Failed: unexpected exception: " + t);
        }
    }

    private static void testFailedFutureWithValidThrowable() {
        Throwable t = new RuntimeException("test exception");
        CompletableFuture<?> cf = CompletableFuture.failedFuture(t);
        if (cf == null) {
            System.err.println("Failed: returned CompletableFuture is null");
            return;
        }
        if (!cf.isDone()) {
            System.err.println("Failed: CompletableFuture is not completed");
            return;
        }
        try {
            cf.get();
            System.err.println("Failed: get() should throw ExecutionException");
        } catch (java.util.concurrent.ExecutionException ee) {
            Throwable cause = ee.getCause();
            if (cause != t && cause.getCause() != t && cause.getClass() != t.getClass()) {
                System.err.println("Failed: cause of ExecutionException does not match original Throwable");
            }
        } catch (Throwable t2) {
            System.err.println("Failed: unexpected exception on get(): " + t2);
        }
    }
}
