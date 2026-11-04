/*
 * @test    @summary Test for CompletableFuture.get() method to ensure it throws UnsupportedOperationException.
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testGetThrowsUnsupported();
    }

    public static void testGetThrowsUnsupported() {
        CompletableFuture<String> future = new CompletableFuture<>();
        try {
            future.get();
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passes if this exception is caught
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
