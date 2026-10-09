/* @test    @summary Testa o método doubles() de java.util.Random para comportamento observável.    @run main RandomWitupTest_P8813c67b9191 */

import java.util.Random;
import java.util.stream.DoubleStream;

public class RandomWitupTest_P8813c67b9191 {
    public static void main(String[] args) {
        testDoublesMethod();
    }

    private static void testDoublesMethod() {
        Random rand = new Random(12345L);
        DoubleStream ds = rand.doubles();
        if (ds == null) {
            throw new AssertionError("FAIL: DoubleStream retornado é null");
        }
        double[] values = ds.limit(10).toArray();
        if (values.length != 10) {
            throw new AssertionError("FAIL: DoubleStream não produziu 10 valores");
        }
        for (double v : values) {
            if (Double.isNaN(v) || Double.isInfinite(v)) {
                throw new AssertionError("FAIL: Valor inválido no DoubleStream: " + v);
            }
        }
        System.out.println("PASS: método doubles() produziu 10 valores válidos.");
    }
}
