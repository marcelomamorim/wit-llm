/* @test    @summary Test for obtrudeException method in CompletableFuture
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testObtrudeExceptionThrows();
    }

    public static void testObtrudeExceptionThrows() {
        CompletableFuture<?> cf = new CompletableFuture<>();
        Throwable ex = new UnsupportedOperationException("Test exception");
        try {
            cf.obtrudeException(ex);
            // Since the method always throws UnsupportedOperationException, this line should not be reached.
            System.out.println("Test failed: no exception thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: UnsupportedOperationException thrown as expected.");
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception: " + t);
        }
    }
}
