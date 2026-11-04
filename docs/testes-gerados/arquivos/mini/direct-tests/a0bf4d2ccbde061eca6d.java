/*
 * @test
 * @summary Test CompletableFuture.obtrudeException throws UnsupportedOperationException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {
    public static void main(String[] args) {
        CompletableFuture<String> cf = new CompletableFuture<>();
        Throwable ex = new RuntimeException("test exception");
        try {
            cf.obtrudeException(ex);
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException uoe) {
            // expected
        }
    }
}
