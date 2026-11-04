/* @test    @summary Test completeExceptionally method of CompletableFuture for unsupported operation    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testCompleteExceptionallyThrowsUnsupported();
    }

    private static void testCompleteExceptionallyThrowsUnsupported() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        Throwable ex = new RuntimeException("Test exception");
        boolean threw = false;
        try {
            cf.completeExceptionally(ex);
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        }
    }
}
