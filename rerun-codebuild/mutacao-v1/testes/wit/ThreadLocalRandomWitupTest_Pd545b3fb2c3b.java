/* @test    @summary Testa o método doubles() de ThreadLocalRandom para comportamento observável.    @run main ThreadLocalRandomWitupTest_Pd545b3fb2c3b */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class ThreadLocalRandomWitupTest_Pd545b3fb2c3b {
    public static void main(String[] args) {
        testDoublesMethod();
    }

    static void testDoublesMethod() {
        DoubleStream ds = ThreadLocalRandom.current().doubles();
        if (ds == null) {
            throw new AssertionError("doubles() retornou null");
        }
        // Testa se o stream não é nulo e produz valores
        double val = ds.findFirst().orElseThrow(() -> new RuntimeException("Stream vazio"));
        // Como o método é aleatório, apenas verifica se o valor é um double válido
        if (Double.isNaN(val) || Double.isInfinite(val)) {
            throw new AssertionError("Valor gerado é inválido: " + val);
        }
        // Opcional: consumir alguns valores para verificar funcionamento
        DoubleStream limited = ds.limit(10);
        if (limited.count() != 10) {
            throw new AssertionError("Stream não produziu 10 valores");
        }
    }
}
