/* @test    @summary Testa o método doubles() de ThreadLocalRandom para comportamento observável.    @run main ThreadLocalRandomWitupTest_Pb89c91541fbf */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class ThreadLocalRandomWitupTest_Pb89c91541fbf {
    public static void main(String[] args) {
        testDoublesReturnsStream();
    }

    public static void testDoublesReturnsStream() {
        DoubleStream ds = ThreadLocalRandom.current().doubles();
        if (ds == null) {
            throw new AssertionError("doubles() retornou null");
        }
        // Testa se o retorno é uma instância de DoubleStream e se pode ser consumido
        try {
            double sum = ds.limit(5).sum();
            if (Double.isNaN(sum)) {
                throw new AssertionError("Resultado da soma é NaN");
            }
        } catch (Exception e) {
            throw new AssertionError("Erro ao consumir DoubleStream: " + e);
        }
    }
}
