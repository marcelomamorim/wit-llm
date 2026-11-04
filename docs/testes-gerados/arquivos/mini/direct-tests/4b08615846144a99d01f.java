/*
 * @test
 * @summary Test java.util.Arrays.setAll(int[], IntUnaryOperator) method
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntUnaryOperator;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testSetAllBasic();
        testSetAllEmptyArray();
        testSetAllGeneratorNull();
    }

    static void testSetAllBasic() {
        int[] array = new int[5];
        IntUnaryOperator generator = new IntUnaryOperator() {
            public int applyAsInt(int operand) {
                return operand * operand;
            }
        };
        Arrays.setAll(array, generator);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != i * i) {
                throw new AssertionError("Element at index " + i + " expected " + (i * i) + " but was " + array[i]);
            }
        }
    }

    static void testSetAllEmptyArray() {
        int[] array = new int[0];
        IntUnaryOperator generator = new IntUnaryOperator() {
            public int applyAsInt(int operand) {
                return 42;
            }
        };
        Arrays.setAll(array, generator);
        if (array.length != 0) {
            throw new AssertionError("Empty array length changed");
        }
    }

    static void testSetAllGeneratorNull() {
        int[] array = new int[3];
        try {
            Arrays.setAll(array, null);
            throw new AssertionError("Expected NullPointerException for null generator");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
