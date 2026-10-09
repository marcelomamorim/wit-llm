/*
 * @test
 * @summary Tests for CompletableFuture.orTimeout method behaviors and exception paths
 * @run main CompletableFutureWitupTest_P539f6e138306
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest_P539f6e138306 {

    public static void main(String[] args) {
        testOrTimeoutNullUnit();
        testOrTimeoutNormalExecution();
    }

    public static void testOrTimeoutNullUnit() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        try {
            cf.orTimeout(100, null);
            System.out.println("FAILED: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASSED: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAILED: Unexpected exception: " + t);
        }
    }

    public static void testOrTimeoutNormalExecution() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        try {
            CompletableFuture<Void> result = cf.orTimeout(10, TimeUnit.MILLISECONDS);
            // Complete the future normally before timeout
            Thread.sleep(5);
            cf.complete(null);
            if (result.isDone() && !result.isCompletedExceptionally()) {
                System.out.println("PASSED: Future completed normally before timeout");
            } else {
                System.out.println("FAILED: Future did not complete as expected");
            }
        } catch (InterruptedException e) {
            System.out.println("FAILED: InterruptedException during test");
        } catch (Throwable t) {
            System.out.println("FAILED: Unexpected exception: " + t);
        }
    }
}
