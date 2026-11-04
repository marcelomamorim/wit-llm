/* @test    @summary Test for CompletableFuture.isCancelled() method behavior.    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testIsCancelledThrowsException();
    }

    public static void testIsCancelledThrowsException() {
        CompletableFuture cf = new CompletableFuture();
        try {
            boolean result = cf.isCancelled();
            // Since the method always throws UnsupportedOperationException,
            // reaching this line indicates a failure.
            System.err.println("Expected UnsupportedOperationException, but got result: " + result);
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown.
            System.out.println("isCancelled() correctly throws UnsupportedOperationException.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
