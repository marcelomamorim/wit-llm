/* @test    @summary Test CompletableFuture.failedStage with null and non-null Throwable    @run main CompletableFutureWitupTest_P9c5ebed0c0bf */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest_P9c5ebed0c0bf {

    public static void main(String[] args) {
        testFailedStageWithNull();
        testFailedStageWithException();
    }

    static void testFailedStageWithNull() {
        try {
            CompletableFuture.failedStage(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testFailedStageWithException() {
        Throwable ex = new RuntimeException("Test exception");
        CompletionStage<?> stage = CompletableFuture.failedStage(ex);
        if (stage == null) {
            System.err.println("failedStage returned null");
            System.exit(1);
        }
        if (!(stage instanceof CompletableFuture)) {
            System.err.println("Returned stage is not a CompletableFuture");
            System.exit(1);
        }
        CompletableFuture<?> cf = (CompletableFuture<?>) stage;
        if (!cf.isCompletedExceptionally()) {
            System.err.println("CompletableFuture is not completed exceptionally");
            System.exit(1);
        }
        try {
            cf.get();
            System.err.println("Expected exception not thrown by get()");
            System.exit(1);
        } catch (Exception e) {
            if (e.getCause() != ex) {
                System.err.println("Cause of exception does not match original");
                System.exit(1);
            }
        }
    }
}
