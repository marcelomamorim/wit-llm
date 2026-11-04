/*
 * @test
 * @summary Test java.util.Arrays.setAll(int[], IntUnaryOperator) for null generator and normal operation
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.IntUnaryOperator;
import java.util.Objects;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testNullGeneratorThrows();
        testSetAllWithValidGenerator();
    }

    private static void testNullGeneratorThrows() {
        int[] array = new int[3];
        IntUnaryOperator generator = null;
        try {
            Arrays.setAll(array, generator);
            throw new AssertionError("Expected NullPointerException for null generator");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testSetAllWithValidGenerator() {
        int[] array = new int[5];
        IntUnaryOperator generator = new IntUnaryOperator() {
            public int applyAsInt(int operand) {
                return operand * operand; // square of index
            }
        };
        Arrays.setAll(array, generator);
        for (int i = 0; i < array.length; i++) {
            int expected = i * i;
            if (array[i] != expected) {
                throw new AssertionError("array[" + i + "] expected: " + expected + ", found: " + array[i]);
            }
        }
    }
}
