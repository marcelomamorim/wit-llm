/*
 * @test
 * @summary Test CompletableFuture.cancel(boolean) throws UnsupportedOperationException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {
    public static void main(String[] args) {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        boolean thrown = false;
        try {
            cf.cancel(true);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown by cancel(true)");
        }
    }
}
