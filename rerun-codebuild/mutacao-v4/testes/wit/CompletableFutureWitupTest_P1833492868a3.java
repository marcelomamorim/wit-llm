/* @test
   @summary Verifies that delayedExecutor throws NullPointerException when unit is null.
   @run main CompletableFutureWitupTest_P1833492868a3
 */
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executor;
public class CompletableFutureWitupTest_P1833492868a3 {
    public static void main(String[] args) {
        // Testa o comportamento quando unit é null
        try {
            java.util.concurrent.CompletableFuture.delayedExecutor(100, null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // esperado
        }
    }
}
