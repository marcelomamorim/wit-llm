/*
 * @test
 * @summary Test java.util.Arrays.parallelSetAll(long[], IntToLongFunction) for correctness and null handling
 * @run main java.util.ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntToLongFunction;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testParallelSetAllBasic();
        testParallelSetAllEmptyArray();
        testParallelSetAllNullGenerator();
    }

    private static void testParallelSetAllBasic() {
        final long[] array = new long[5];
        IntToLongFunction generator = new IntToLongFunction() {
            public long applyAsLong(int i) {
                return i * 10L;
            }
        };
        Arrays.parallelSetAll(array, generator);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != i * 10L) {
                throw new AssertionError("parallelSetAll failed at index " + i + ": expected " + (i * 10L) + ", got " + array[i]);
            }
        }
    }

    private static void testParallelSetAllEmptyArray() {
        final long[] array = new long[0];
        IntToLongFunction generator = new IntToLongFunction() {
            public long applyAsLong(int i) {
                throw new AssertionError("Generator should not be called for empty array");
            }
        };
        Arrays.parallelSetAll(array, generator);
        // If no exception and no generator call, test passes
    }

    private static void testParallelSetAllNullGenerator() {
        final long[] array = new long[1];
        try {
            Arrays.parallelSetAll(array, null);
            throw new AssertionError("Expected NullPointerException for null generator");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
