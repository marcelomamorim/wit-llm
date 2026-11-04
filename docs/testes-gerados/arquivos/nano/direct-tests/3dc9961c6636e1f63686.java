/* @test    @summary Test CompletableFuture.orTimeout method for timeout behavior    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testOrTimeoutWithNullUnit();
        testOrTimeoutNormalFlow();
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

    private static void testOrTimeoutNormalFlow() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        cf.orTimeout(1, TimeUnit.MILLISECONDS);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // ignore
        }
        if (cf.isDone()) {
            try {
                cf.get();
                System.out.println("FAIL: CompletableFuture should have timed out and completed exceptionally");
            } catch (Exception e) {
                System.out.println("PASS: CompletableFuture completed exceptionally as expected");
            }
        } else {
            System.out.println("FAIL: CompletableFuture not completed");
        }
    }
}
