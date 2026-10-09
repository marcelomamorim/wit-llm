/* @test
   @summary Testa o método doubles() de ThreadLocalRandom.
   @run main ThreadLocalRandomWitupTest_P308097fc73a2
 */
import java.util.concurrent.ThreadLocalRandom;
public class ThreadLocalRandomWitupTest_P308097fc73a2 {
    public static void main(String[] args) {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        java.util.stream.DoubleStream stream = r.doubles();
        if (stream == null) {
            throw new AssertionError("Stream should not be null");
        }
        // Testa se o stream produz valores Double válidos
        double val = stream.findFirst().orElseThrow(() -> new AssertionError("Stream vazio"));
        if (Double.isNaN(val) || Double.isInfinite(val)) {
            throw new AssertionError("Valor inválido: " + val);
        }
        stream.close();
    }
}
