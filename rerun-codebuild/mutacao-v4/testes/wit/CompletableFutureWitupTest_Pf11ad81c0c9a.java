/* @test
   @summary Verifica comportamento do método failedFuture com entrada nula.
   @run main CompletableFutureWitupTest_Pf11ad81c0c9a
 */
import java.util.concurrent.CompletableFuture;
public class CompletableFutureWitupTest_Pf11ad81c0c9a {
    public static void main(String[] args) {
        try {
            CompletableFuture.failedFuture(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
