/* @test    @summary Test for CompletableFuture.delayedExecutor(long, TimeUnit) method with null unit argument.
 * @run main CompletableFutureWitupTest_P805153f0f5af
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executor;

public class CompletableFutureWitupTest_P805153f0f5af {

    public static void main(String[] args) {
        testDelayedExecutorNullUnit();
        testDelayedExecutorValidArguments();
    }

    public static void testDelayedExecutorNullUnit() {
        try {
            java.util.concurrent.Executor executor = java.util.concurrent.CompletableFuture.delayedExecutor(100, null);
            // If no exception, test fails
            System.err.println("FAIL: Expected NullPointerException for null TimeUnit");
        } catch (NullPointerException expected) {
            // Test passes
            System.out.println("PASS: NullPointerException thrown as expected for null TimeUnit");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception type: " + t);
        }
    }

    public static void testDelayedExecutorValidArguments() {
        long delay = 50;
        TimeUnit unit = TimeUnit.MILLISECONDS;
        Executor executor = java.util.concurrent.CompletableFuture.delayedExecutor(delay, unit);
        if (executor != null) {
            System.out.println("PASS: Executor returned successfully with valid arguments");
        } else {
            System.err.println("FAIL: Executor is null with valid arguments");
        }
    }
}
