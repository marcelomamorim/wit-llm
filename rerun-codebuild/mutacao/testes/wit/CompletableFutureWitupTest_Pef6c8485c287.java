/*
 * @test
 * @summary Test for completeExceptionally method in CompletableFuture
 * @run main CompletableFutureWitupTest_Pef6c8485c287
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest_Pef6c8485c287 {

    public static void main(String[] args) {
        testCompleteExceptionallyWithNull();
        testCompleteExceptionallyWithValidThrowable();
    }

    private static void testCompleteExceptionallyWithNull() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        try {
            cf.completeExceptionally(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testCompleteExceptionallyWithValidThrowable() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        Throwable t = new RuntimeException("Test exception");
        boolean result = cf.completeExceptionally(t);
        if (result) {
            if (cf.isCompletedExceptionally()) {
                System.out.println("PASS: CompletableFuture completed exceptionally as expected");
            } else {
                System.out.println("FAIL: CompletableFuture not marked as completed exceptionally");
            }
        } else {
            System.out.println("FAIL: completeExceptionally returned false");
        }
    }
}
