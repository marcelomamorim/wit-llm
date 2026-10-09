/* @test
   @summary Testa o método completeExceptionally com entrada nula.
   @run main CompletableFutureWitupTest_Pe1a2a035e6f5
 */
import java.util.concurrent.CompletableFuture;
public class CompletableFutureWitupTest_Pe1a2a035e6f5 {
    public static void main(String[] args) throws Exception {
        CompletableFuture cf = new CompletableFuture();
        try {
            cf.completeExceptionally(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // esperado
        }
    }
}
