/* @test    @summary Test for CompletableFuture.failedFuture(Throwable) method
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testFailedFutureWithNull();
        testFailedFutureWithException();
    }

    private static void testFailedFutureWithNull() {
        try {
            CompletableFuture.failedFuture(null);
            System.err.println("Failed: expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected for null argument");
        } catch (Throwable t) {
            System.err.println("Failed: unexpected exception type: " + t);
        }
    }

    private static void testFailedFutureWithException() {
        Throwable ex = new RuntimeException("test exception");
        CompletableFuture<Void> cf = CompletableFuture.failedFuture(ex);
        if (cf.isCompletedExceptionally()) {
            try {
                cf.get();
                System.err.println("Failed: get() should throw an exception");
            } catch (Throwable t) {
                if (t.getCause() == ex || t.getCause().getMessage().equals("test exception")) {
                    System.out.println("Passed: CompletableFuture failedFuture returns a future completed exceptionally with the correct exception");
                } else {
                    System.err.println("Failed: exception cause mismatch");
                }
            }
        } else {
            System.err.println("Failed: CompletableFuture is not completed exceptionally");
        }
    }
}
