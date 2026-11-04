/*
 * @test
 * @summary Test CompletableFuture.completeExceptionally throws UnsupportedOperationException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {
    public static void main(String[] args) {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        Throwable ex = new RuntimeException("test exception");
        boolean thrown = false;
        try {
            cf.completeExceptionally(ex);
        } catch (UnsupportedOperationException uoe) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        }
    }
}
