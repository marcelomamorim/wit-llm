/*
 * @test
 * @summary Test java.util.Arrays.setAll(double[], IntToDoubleFunction) method
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntToDoubleFunction;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testSetAllBasic();
        testSetAllEmptyArray();
        testSetAllNullGenerator();
    }

    private static void testSetAllBasic() {
        double[] array = new double[5];
        IntToDoubleFunction generator = new IntToDoubleFunction() {
            public double applyAsDouble(int i) {
                return i * 2.0;
            }
        };
        Arrays.setAll(array, generator);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != i * 2.0) {
                throw new AssertionError("Element at index " + i + " expected " + (i * 2.0) + " but was " + array[i]);
            }
        }
    }

    private static void testSetAllEmptyArray() {
        double[] array = new double[0];
        IntToDoubleFunction generator = new IntToDoubleFunction() {
            public double applyAsDouble(int i) {
                throw new AssertionError("Generator should not be called for empty array");
            }
        };
        Arrays.setAll(array, generator);
        if (array.length != 0) {
            throw new AssertionError("Array length expected 0 but was " + array.length);
        }
    }

    private static void testSetAllNullGenerator() {
        double[] array = new double[1];
        try {
            Arrays.setAll(array, null);
            throw new AssertionError("Expected NullPointerException for null generator");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
