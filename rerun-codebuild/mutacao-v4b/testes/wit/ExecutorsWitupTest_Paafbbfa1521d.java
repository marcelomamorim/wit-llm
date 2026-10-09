/* @test
   @summary Testa o método unconfigurableExecutorService com entrada nula.
   @run main ExecutorsWitupTest_Paafbbfa1521d
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class ExecutorsWitupTest_Paafbbfa1521d {
    public static void main(String[] args) {
        // Teste de entrada nula, deve lançar NullPointerException
        try {
            Executors.unconfigurableExecutorService(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // esperado
        }
    }
}
