/* @test    @summary Test isCancelled method of CompletableFuture for unsupported operation behavior    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testIsCancelledThrowsUnsupported();
    }

    public static void testIsCancelledThrowsUnsupported() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        try {
            cf.isCancelled();
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is caught
        }
    }
}
