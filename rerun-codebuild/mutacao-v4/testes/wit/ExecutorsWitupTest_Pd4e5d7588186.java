/* @test
   @summary Testa o método Executors.callable com entrada nula.
   @run main ExecutorsWitupTest_Pd4e5d7588186
 */
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
public class ExecutorsWitupTest_Pd4e5d7588186 {
    public static void main(String[] args) {
        try {
            Executors.callable(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // esperado
        }
    }
}
