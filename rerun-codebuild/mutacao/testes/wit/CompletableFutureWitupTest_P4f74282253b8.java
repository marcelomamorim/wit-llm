/*
 * @test
 * @summary Test for CompletableFuture.orTimeout method behavior
 * @run main CompletableFutureWitupTest_P4f74282253b8
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest_P4f74282253b8 {

    public static void main(String[] args) {
        testOrTimeoutWithNullUnit();
        testOrTimeoutWithValidParameters();
    }

    private static void testOrTimeoutWithNullUnit() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        try {
            cf.orTimeout(1, null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testOrTimeoutWithValidParameters() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        try {
            CompletableFuture<Void> result = cf.orTimeout(100, TimeUnit.MILLISECONDS);
            if (result == cf) {
                System.out.println("PASS: orTimeout returns the same CompletableFuture instance");
            } else {
                System.out.println("FAIL: orTimeout does not return the same instance");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
