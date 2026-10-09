/* @test
   @summary Test completeExceptionally with null and non-null Throwable.
   @run main CompletableFutureWitupTest_Pe1a2a035e6f5
 */
import java.util.concurrent.CompletableFuture;
public class CompletableFutureWitupTest_Pe1a2a035e6f5 {
    public static void main(String[] args) {
        testCompleteExceptionallyWithNull();
        testCompleteExceptionallyWithThrowable();
    }
    private static void testCompleteExceptionallyWithNull() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        try {
            cf.completeExceptionally(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        if (cf.isDone()) {
            throw new AssertionError("Future should not be completed when null exception is passed");
        }
    }
    private static void testCompleteExceptionallyWithThrowable() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        Throwable t = new Throwable();
        boolean result = cf.completeExceptionally(t);
        if (!result) {
            throw new AssertionError("completeExceptionally should return true when completing a new future");
        }
        if (!cf.isDone()) {
            throw new AssertionError("Future should be completed after completeExceptionally");
        }
        if (cf.isCompletedExceptionally() == false) {
            throw new AssertionError("Future should be marked as completed exceptionally");
        }
        try {
            cf.get();
            throw new AssertionError("get() should throw an exception");
        } catch (java.util.concurrent.ExecutionException e) {
            if (e.getCause() != t) {
                throw new AssertionError("Cause of ExecutionException should be the throwable passed to completeExceptionally");
            }
        } catch (InterruptedException e) {
            throw new AssertionError("InterruptedException should not be thrown here");
        }
    }
}
