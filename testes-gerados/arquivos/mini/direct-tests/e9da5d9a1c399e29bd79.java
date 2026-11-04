/*
 * @test
 * @summary Test CompletableFuture.get() throws UnsupportedOperationException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {
    public static void main(String[] args) {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        try {
            cf.get();
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        } catch (Throwable t) {
            throw new Error("Unexpected exception type: " + t);
        }
    }
}
