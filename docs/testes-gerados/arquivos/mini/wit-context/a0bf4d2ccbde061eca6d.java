/*
 * @test
 * @summary Test CompletableFuture.obtrudeException throws UnsupportedOperationException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {
    public static void main(String[] args) {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        Throwable ex = new Throwable("test exception");
        try {
            cf.obtrudeException(ex);
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        } catch (Throwable t) {
            throw new Error("Unexpected exception type: " + t);
        }
    }
}
