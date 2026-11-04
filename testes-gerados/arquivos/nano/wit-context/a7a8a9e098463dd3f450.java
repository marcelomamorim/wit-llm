/*
 * @test
 * @summary Testa o método Arrays.parallelSetAll para entradas válidas e inválidas.
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.IntToDoubleFunction;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testParallelSetAllWithNullGenerator();
        testParallelSetAllWithValidGenerator();
    }

    private static void testParallelSetAllWithNullGenerator() {
        double[] array = {1.0, 2.0, 3.0};
        try {
            Arrays.parallelSetAll(array, null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Test passed
        }
    }

    private static void testParallelSetAllWithValidGenerator() {
        double[] array = new double[3];
        IntToDoubleFunction generator = i -> i * 2.0;
        Arrays.parallelSetAll(array, generator);
        if (array.length != 3) {
            throw new AssertionError("Array length mismatch");
        }
        if (array[0] != 0.0 || array[1] != 2.0 || array[2] != 4.0) {
            throw new AssertionError("Array values incorrect: " + java.util.Arrays.toString(array));
        }
    }
}
