/* @test    @summary Testa o método ints() de java.util.Random para comportamento observável.    @run main RandomWitupTest_P185e6b912bd1 */

import java.util.Random;
import java.util.stream.IntStream;

public class RandomWitupTest_P185e6b912bd1 {

    public static void main(String[] args) {
        testIntsDefault();
        testIntsWithSeed();
    }

    static void testIntsDefault() {
        Random rand = new Random();
        IntStream stream = rand.ints();
        int[] values = stream.limit(5).toArray();
        boolean allEqual = true;
        for (int i = 1; i < values.length; i++) {
            if (values[i] != values[0]) {
                allEqual = false;
                break;
            }
        }
        if (values.length != 5) {
            throw new AssertionError("Expected 5 values, got " + values.length);
        }
        if (allEqual) {
            throw new AssertionError("All generated values are equal, which is unlikely.");
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
        for (int i = 0; i < 5; i++) {
            if (vals1[i] != vals2[i]) {
                throw new AssertionError("Sequences with same seed differ at index " + i);
            }
        }
    }
}
