/* @test
   @summary Testa o método failedStage com entrada nula.
   @run main CompletableFutureWitupTest_Pc9e45fae431b
 */
import java.util.concurrent.CompletableFuture;
public class CompletableFutureWitupTest_Pc9e45fae431b {
    public static void main(String[] args) {
        // Testa o comportamento quando ex é null, esperando NullPointerException
        try {
            CompletableFuture.failedStage(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // esperado
        }
    }
}
