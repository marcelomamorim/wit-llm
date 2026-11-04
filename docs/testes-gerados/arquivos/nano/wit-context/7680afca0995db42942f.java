/* @test    @summary Test for obtrudeValue method in CompletableFuture to verify exception throwing behavior    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testObtrudeValueThrowsException();
    }

    public static void testObtrudeValueThrowsException() {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        try {
            cf.obtrudeValue("test");
            // If no exception, test fails
            System.err.println("Failed: obtrudeValue did not throw exception");
        } catch (UnsupportedOperationException e) {
            // Expected exception
            System.out.println("Passed: obtrudeValue threw UnsupportedOperationException as expected");
        } catch (Throwable t) {
            System.err.println("Failed: obtrudeValue threw unexpected exception: " + t);
        }
    }
}
