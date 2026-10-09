/* @test
   @summary Testa o método orTimeout para verificar comportamento com entrada nula.
   @run main CompletableFutureWitupTest_P3462dcb845d4
 */
import java.util.concurrent.TimeUnit;
public class CompletableFutureWitupTest_P3462dcb845d4 {
    public static void main(String[] args) throws Exception {
        java.util.concurrent.CompletableFuture<Void> future = new java.util.concurrent.CompletableFuture<>();
        try {
            future.orTimeout(1, null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // esperado
        }
    }
}
