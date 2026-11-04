/* @test    @summary Test getNow method of CompletableFuture for unsupported operation exception    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testGetNowThrowsException();
    }

    public static void testGetNowThrowsException() {
        CompletableFuture<String> future = new CompletableFuture<>();
        try {
            future.getNow("default");
            // If no exception, fail the test
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
