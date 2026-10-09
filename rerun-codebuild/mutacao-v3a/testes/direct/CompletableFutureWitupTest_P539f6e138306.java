/* @test    @summary Test CompletableFuture.orTimeout() method behavior    @run main CompletableFutureWitupTest_P539f6e138306 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureWitupTest_P539f6e138306 {

    public static void main(String[] args) {
        testOrTimeoutWithValidTimeout();
        testOrTimeoutWithNullUnit();
        testOrTimeoutOnCompletedFuture();
    }

    private static void testOrTimeoutWithValidTimeout() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.orTimeout(1, TimeUnit.MILLISECONDS);
        boolean completed = cf.isDone();
        System.out.println("testOrTimeoutWithValidTimeout: isDone=" + completed);
        try {
            cf.get();
        } catch (Exception e) {
            System.out.println("Expected exception due to timeout: " + e);
        }
    }

    private static void testOrTimeoutWithNullUnit() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        try {
            cf.orTimeout(1, null);
            System.out.println("testOrTimeoutWithNullUnit: Failed to throw NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testOrTimeoutWithNullUnit: Caught expected NullPointerException");
        }
    }

    private static void testOrTimeoutOnCompletedFuture() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete("done");
        cf.orTimeout(10, TimeUnit.MILLISECONDS);
        if (cf.isDone() && !cf.isCompletedExceptionally()) {
            System.out.println("testOrTimeoutOnCompletedFuture: Future remains completed with result: " + cf.getNow(null));
        } else {
            System.out.println("testOrTimeoutOnCompletedFuture: Future not completed as expected");
        }
    }
}
