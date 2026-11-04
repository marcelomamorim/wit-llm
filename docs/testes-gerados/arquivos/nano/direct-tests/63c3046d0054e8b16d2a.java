/* @test    @summary Test cancel method of CompletableFuture for unsupported operation    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testCancelReturnsUnsupported();
    }

    public static void testCancelReturnsUnsupported() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        boolean thrown = false;
        try {
            future.cancel(true);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException to be thrown");
        }
    }
}
