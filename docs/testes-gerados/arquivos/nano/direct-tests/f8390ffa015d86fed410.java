/* @test    @summary Test the complete method of CompletableFuture for successful completion.    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testCompleteReturnsTrueWhenCompletesSuccessfully();
        testCompleteReturnsFalseWhenAlreadyCompleted();
    }

    public static void testCompleteReturnsTrueWhenCompletesSuccessfully() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        boolean result = cf.complete("test");
        if (!result) {
            throw new AssertionError("Expected complete() to return true on first call");
        }
        if (!cf.isDone()) {
            throw new AssertionError("CompletableFuture should be done after complete()");
        }
        if (!"test".equals(cf.getNow(null))) {
            throw new AssertionError("CompletableFuture value mismatch");
        }
    }

    public static void testCompleteReturnsFalseWhenAlreadyCompleted() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete("initial");
        boolean result = cf.complete("second");
        if (result) {
            throw new AssertionError("Expected complete() to return false when already completed");
        }
        if (!"initial".equals(cf.getNow(null))) {
            throw new AssertionError("CompletableFuture value should not change after second complete()");
        }
    }
}
