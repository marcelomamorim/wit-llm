/* @test    @summary Test isDone() method of CompletableFuture for default behavior    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testIsDoneReturnsFalseForNewInstance();
        testIsDoneReturnsTrueAfterComplete();
        testIsDoneReturnsTrueAfterCompleteExceptionally();
        testIsDoneReturnsTrueAfterCancel();
    }

    private static void testIsDoneReturnsFalseForNewInstance() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        boolean result = cf.isDone();
        if (result != false) {
            throw new AssertionError("Expected isDone() to return false for new CompletableFuture");
        }
    }

    private static void testIsDoneReturnsTrueAfterComplete() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        cf.complete(null);
        boolean result = cf.isDone();
        if (result != true) {
            throw new AssertionError("Expected isDone() to return true after complete()");
        }
    }

    private static void testIsDoneReturnsTrueAfterCompleteExceptionally() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        cf.completeExceptionally(new RuntimeException());
        boolean result = cf.isDone();
        if (result != true) {
            throw new AssertionError("Expected isDone() to return true after completeExceptionally()");
        }
    }

    private static void testIsDoneReturnsTrueAfterCancel() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        cf.cancel(true);
        boolean result = cf.isDone();
        if (result != true) {
            throw new AssertionError("Expected isDone() to return true after cancel()");
        }
    }
}
