/* @test
   @summary Testa o método privilegedCallable para verificar comportamento com entrada nula.
   @run main ExecutorsWitupTest_P644c0236b162
 */
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
public class ExecutorsWitupTest_P644c0236b162 {
    public static void main(String[] args) {
        try {
            Executors.privilegedCallable(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // esperado
        }
    }
}
