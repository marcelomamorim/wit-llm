/* @test    @summary Test obtrudeException method of CompletableFuture    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testObtrudeExceptionThrowsUnsupported();
    }

    private static void testObtrudeExceptionThrowsUnsupported() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        try {
            cf.obtrudeException(new RuntimeException("Test exception"));
            // Expecting UnsupportedOperationException
        } catch (UnsupportedOperationException e) {
            System.out.println("Passed: obtrudeException throws UnsupportedOperationException as expected.");
            return;
        }
        throw new AssertionError("obtrudeException did not throw UnsupportedOperationException");
    }
}
