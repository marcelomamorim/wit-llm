/*
 * @test
 * @summary Test java.util.Arrays.setAll(double[], IntToDoubleFunction) for null generator and normal operation
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntToDoubleFunction;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testSetAllWithNullGenerator();
        testSetAllWithValidGenerator();
    }

    private static void testSetAllWithNullGenerator() {
        double[] array = new double[3];
        try {
            Arrays.setAll(array, null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testSetAllWithValidGenerator() {
        double[] array = new double[5];
        IntToDoubleFunction generator = new IntToDoubleFunction() {
            public double applyAsDouble(int index) {
                return index * 2.0;
            }
        };
        Arrays.setAll(array, generator);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != i * 2.0) {
                throw new RuntimeException("Array element at index " + i + " expected " + (i * 2.0) + " but was " + array[i]);
            }
        }
    }
}
