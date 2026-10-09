/* @test
   @summary Tests the execute method of ForkJoinPool for null input.
   @run main ForkJoinPoolWitupTest_P9f54802386b3
 */
import java.util.concurrent.ForkJoinPool;
public class ForkJoinPoolWitupTest_P9f54802386b3 {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        // Testa execução com null, espera NullPointerException
        try {
            pool.execute(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // esperado
        }
    }
}
