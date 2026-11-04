/* @test    @summary Test for getNumberOfDependents method in CompletableFuture
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testGetNumberOfDependentsThrowsException();
    }

    public static void testGetNumberOfDependentsThrowsException() {
        CompletableFuture<?> cf = new CompletableFuture<>();
        try {
            int result = cf.getNumberOfDependents();
            if (result != 0) {
                System.out.println("Test failed: Expected 0, got " + result);
            } else {
                System.out.println("Test passed: getNumberOfDependents() returned 0 as expected.");
            }
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: getNumberOfDependents() threw UnsupportedOperationException as expected.");
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception: " + t);
        }
    }
}
