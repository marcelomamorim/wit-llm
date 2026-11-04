/*
 * @test
 * @summary Test java.util.Arrays.parallelSetAll(int[], IntUnaryOperator) for null generator and normal operation
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testNullGeneratorThrows();
        testParallelSetAllBasic();
    }

    private static void testNullGeneratorThrows() {
        int[] array = new int[5];
        IntUnaryOperator generator = null;
        try {
            Arrays.parallelSetAll(array, generator);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testParallelSetAllBasic() {
        final int length = 10;
        int[] array = new int[length];
        IntUnaryOperator generator = new IntUnaryOperator() {
            public int applyAsInt(int operand) {
                return operand * operand; // square function
            }
        };
        Arrays.parallelSetAll(array, generator);
        for (int i = 0; i < length; i++) {
            int expected = i * i;
            if (array[i] != expected) {
                throw new RuntimeException("parallelSetAll failed at index " + i + ": expected " + expected + ", got " + array[i]);
            }
        }
    }
}
