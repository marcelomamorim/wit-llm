/* @test    @summary Test for completeExceptionally method in CompletableFuture
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testCompleteExceptionallyThrowsUnsupported();
    }

    public static void testCompleteExceptionallyThrowsUnsupported() {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        try {
            boolean result = cf.completeExceptionally(new UnsupportedOperationException());
            if (result != false) {
                System.err.println("Expected completeExceptionally to return false");
                System.exit(1);
            }
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            System.out.println("Caught expected UnsupportedOperationException");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
