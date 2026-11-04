/*
 * @test
 * @summary Test java.util.Arrays.parallelSetAll(double[], IntToDoubleFunction) for null and normal usage
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntToDoubleFunction;
import java.util.stream.IntStream;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testNullGenerator();
        testNormalFunctionality();
    }

    private static void testNullGenerator() {
        double[] array = new double[5];
        IntToDoubleFunction generator = null;
        try {
            Arrays.parallelSetAll(array, generator);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testNormalFunctionality() {
        final int length = 10;
        double[] array = new double[length];
        IntToDoubleFunction generator = new IntToDoubleFunction() {
            public double applyAsDouble(int value) {
                return value * 2.0;
            }
        };

        Arrays.parallelSetAll(array, generator);

        for (int i = 0; i < length; i++) {
            if (array[i] != i * 2.0) {
                throw new RuntimeException("Value at index " + i + " expected " + (i * 2.0) + " but found " + array[i]);
            }
        }
    }
}
