/* @test    @summary Test for CompletableFuture.orTimeout with null TimeUnit    @run main CompletableFutureWitupTest_P6c85d1aaf7f8 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest_P6c85d1aaf7f8 {

    public static void main(String[] args) {
        testOrTimeoutWithNullUnit();
        testOrTimeoutNormalBehavior();
    }

    private static void testOrTimeoutWithNullUnit() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        try {
            cf.orTimeout(1, null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }

    private static void testOrTimeoutNormalBehavior() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        cf.orTimeout(1, TimeUnit.MILLISECONDS);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (!cf.isDone()) {
            throw new AssertionError("CompletableFuture should be completed after timeout");
        }
        if (cf.isCompletedExceptionally()) {
            throw new AssertionError("CompletableFuture completed exceptionally");
        }
    }
}
