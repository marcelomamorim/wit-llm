/* @test    @summary Test for CompletableFuture.delayedExecutor with null TimeUnit to verify NullPointerException is thrown    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testDelayedExecutorWithNullTimeUnit();
    }

    public static void testDelayedExecutorWithNullTimeUnit() {
        long delay = 1000L;
        try {
            CompletableFuture.delayedExecutor(delay, null);
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Test passes if exception is thrown
            System.out.println("NullPointerException correctly thrown.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception type: " + t);
            System.exit(1);
        }
    }
}
