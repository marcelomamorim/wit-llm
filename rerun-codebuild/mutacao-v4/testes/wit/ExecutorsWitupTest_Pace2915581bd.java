/* @test
   @summary Testa o método privilegedCallableUsingCurrentClassLoader para verificar comportamento com entrada nula.
   @run main ExecutorsWitupTest_Pace2915581bd
 */
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
public class ExecutorsWitupTest_Pace2915581bd {
    public static void main(String[] args) throws Exception {
        // Testa o comportamento quando callable é null, esperando NullPointerException
        try {
            Executors.privilegedCallableUsingCurrentClassLoader(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // esperado
        }
    }
}
