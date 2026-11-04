/*
 * @test
 * @summary Test CompletableFuture.complete(T) throws UnsupportedOperationException as per MinimalStage
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {
    public static void main(String[] args) {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        boolean thrown = false;
        try {
            cf.complete(null);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown by complete(null)");
        }
    }
}
