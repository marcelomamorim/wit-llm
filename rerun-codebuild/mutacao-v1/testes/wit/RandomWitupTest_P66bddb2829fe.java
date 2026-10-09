/* @test    @summary Testa o método ints() de java.util.Random para comportamento observável.    @run main RandomWitupTest_P66bddb2829fe */

import java.util.Random;
import java.util.stream.IntStream;

public class RandomWitupTest_P66bddb2829fe {
    public static void main(String[] args) {
        testIntsDefault();
        testIntsWithSeed();
    }

    static void testIntsDefault() {
        Random rand = new Random();
        IntStream stream = rand.ints();
        int[] values = stream.limit(5).toArray();
        if (values.length != 5) {
            throw new AssertionError("Expected 5 elements, got " + values.length);
        }
        // Verifica se os valores estão dentro do intervalo esperado (int válido)
        for (int v : values) {
            if (v == Integer.MIN_VALUE || v == Integer.MAX_VALUE) {
                // Apenas uma verificação básica, valores podem ser extremos
                continue;
            }
        }
    }

    static void testIntsWithSeed() {
        long seed = 12345L;
        Random rand1 = new Random(seed);
        Random rand2 = new Random(seed);
        IntStream stream1 = rand1.ints();
        IntStream stream2 = rand2.ints();
        int[] vals1 = stream1.limit(5).toArray();
        int[] vals2 = stream2.limit(5).toArray();
        if (vals1.length != 5 || vals2.length != 5) {
            throw new AssertionError("Expected 5 elements in both streams");
        }
        for (int i = 0; i < 5; i++) {
            if (vals1[i] != vals2[i]) {
                throw new AssertionError("Streams with same seed produced different values at index " + i);
            }
        }
    }
}
