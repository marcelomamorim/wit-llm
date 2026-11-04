/* @test    @summary Test delayedExecutor with valid delay and unit    @run main CompletableFutureWitupTest */

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testDelayedExecutorWithValidParameters();
        testDelayedExecutorWithNullUnit();
    }

    static void testDelayedExecutorWithValidParameters() {
        long delay = 100L;
        TimeUnit unit = TimeUnit.MILLISECONDS;
        Executor executor = java.util.concurrent.CompletableFuture.delayedExecutor(delay, unit);
        assert executor != null : "Executor should not be null for valid delay and unit";
    }

    static void testDelayedExecutorWithNullUnit() {
        long delay = 100L;
        try {
            java.util.concurrent.CompletableFuture.delayedExecutor(delay, null);
            assert false : "NullPointerException expected when unit is null";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
