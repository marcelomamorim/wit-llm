/*
 * @test
 * @summary Test java.util.Arrays.parallelSetAll(double[], IntToDoubleFunction) for correctness and null handling
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntToDoubleFunction;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testParallelSetAllBasic();
        testParallelSetAllEmptyArray();
        testParallelSetAllNullGenerator();
    }

    private static void testParallelSetAllBasic() {
        final double[] array = new double[5];
        IntToDoubleFunction generator = new IntToDoubleFunction() {
            public double applyAsDouble(int i) {
                return i * 2.0;
            }
        };
        Arrays.parallelSetAll(array, generator);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != i * 2.0) {
                throw new AssertionError("Element at index " + i + " expected " + (i * 2.0) + " but was " + array[i]);
            }
        }
    }

    private static void testParallelSetAllEmptyArray() {
        final double[] array = new double[0];
        IntToDoubleFunction generator = new IntToDoubleFunction() {
            public double applyAsDouble(int i) {
                throw new AssertionError("Generator should not be called for empty array");
            }
        };
        Arrays.parallelSetAll(array, generator);
        if (array.length != 0) {
            throw new AssertionError("Array length expected 0 but was " + array.length);
        }
    }

    private static void testParallelSetAllNullGenerator() {
        final double[] array = new double[1];
        try {
            Arrays.parallelSetAll(array, null);
            throw new AssertionError("Expected NullPointerException for null generator");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
