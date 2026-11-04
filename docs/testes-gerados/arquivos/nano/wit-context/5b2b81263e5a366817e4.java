/* @test    @summary Test for CompletableFuture.isCompletedExceptionally() method.
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testIsCompletedExceptionallyThrows();
        testIsCompletedExceptionallyReturnsFalseWhenNotExceptional();
    }

    private static void testIsCompletedExceptionallyThrows() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        try {
            cf.isCompletedExceptionally();
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: UnsupportedOperationException thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testIsCompletedExceptionallyReturnsFalseWhenNotExceptional() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        boolean result = cf.isCompletedExceptionally();
        if (result == false) {
            System.out.println("PASS: isCompletedExceptionally() returns false for incomplete future.");
        } else {
            System.out.println("FAIL: isCompletedExceptionally() returned true unexpectedly.");
        }
    }
}
