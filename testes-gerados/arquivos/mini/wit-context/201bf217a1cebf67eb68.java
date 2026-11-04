/*
 * @test
 * @summary Test java.util.Arrays.parallelSetAll(long[], IntToLongFunction) for null generator and normal operation
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.IntToLongFunction;
import java.util.Objects;
import java.util.stream.IntStream;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testNullGenerator();
        testNormalOperation();
    }

    private static void testNullGenerator() {
        long[] array = new long[5];
        try {
            Arrays.parallelSetAll(array, null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testNormalOperation() {
        final int length = 10;
        long[] array = new long[length];
        IntToLongFunction generator = new IntToLongFunction() {
            public long applyAsLong(int value) {
                return value * 2L;
            }
        };
        Arrays.parallelSetAll(array, generator);
        for (int i = 0; i < length; i++) {
            if (array[i] != i * 2L) {
                throw new RuntimeException("Array element at index " + i + " expected " + (i * 2L) + " but found " + array[i]);
            }
        }
    }
}
