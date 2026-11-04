/*
 * @test
 * @summary Test java.util.Arrays.parallelSetAll(int[], IntUnaryOperator) for correctness and null handling
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntUnaryOperator;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testParallelSetAllBasic();
        testParallelSetAllEmptyArray();
        testParallelSetAllNullGenerator();
    }

    private static void testParallelSetAllBasic() {
        int[] array = new int[5];
        IntUnaryOperator generator = new IntUnaryOperator() {
            public int applyAsInt(int operand) {
                return operand * operand;
            }
        };
        Arrays.parallelSetAll(array, generator);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != i * i) {
                throw new AssertionError("parallelSetAll failed at index " + i + ": expected " + (i * i) + ", got " + array[i]);
            }
        }
    }

    private static void testParallelSetAllEmptyArray() {
        int[] array = new int[0];
        IntUnaryOperator generator = new IntUnaryOperator() {
            public int applyAsInt(int operand) {
                return 42;
            }
        };
        Arrays.parallelSetAll(array, generator);
        if (array.length != 0) {
            throw new AssertionError("parallelSetAll failed on empty array: length changed");
        }
    }

    private static void testParallelSetAllNullGenerator() {
        int[] array = new int[1];
        try {
            Arrays.parallelSetAll(array, null);
            throw new AssertionError("parallelSetAll did not throw NullPointerException when generator is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
