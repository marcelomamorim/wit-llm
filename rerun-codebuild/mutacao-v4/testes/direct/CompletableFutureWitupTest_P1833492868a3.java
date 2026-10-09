/* @test
   @summary Tests the delayedExecutor static method of CompletableFuture.
   @run main CompletableFutureWitupTest_P1833492868a3
 */
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executor;
public class CompletableFutureWitupTest_P1833492868a3 {
    public static void main(String[] args) {
        // Test that passing null as TimeUnit throws NullPointerException
        try {
            CompletableFuture.delayedExecutor(100, null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        // Test that a valid delay and TimeUnit returns an Executor
        Executor executor = CompletableFuture.delayedExecutor(50, TimeUnit.MILLISECONDS);
        if (executor == null) {
            throw new AssertionError("Executor should not be null");
        }
    }
}
