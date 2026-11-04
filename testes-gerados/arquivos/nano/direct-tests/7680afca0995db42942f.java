/* @test    @summary Test obtrudeValue method of CompletableFuture for UnsupportedOperationException    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testObtrudeValueThrows();
    }

    public static void testObtrudeValueThrows() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        try {
            cf.obtrudeValue("test");
            // If no exception, test fails
            System.err.println("Failed: obtrudeValue did not throw UnsupportedOperationException");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            System.out.println("Passed: obtrudeValue threw UnsupportedOperationException as expected");
        } catch (Throwable t) {
            System.err.println("Failed: obtrudeValue threw unexpected exception: " + t);
            System.exit(1);
        }
    }
}
