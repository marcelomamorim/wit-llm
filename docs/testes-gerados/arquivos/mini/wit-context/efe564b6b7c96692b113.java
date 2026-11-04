/*
 * @test
 * @summary Test java.util.Arrays.parallelSetAll with null and valid generator
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testNullGeneratorThrows();
        testValidGeneratorSetsArray();
    }

    private static void testNullGeneratorThrows() {
        String[] array = new String[1];
        try {
            Arrays.parallelSetAll(array, null);
            throw new AssertionError("Expected NullPointerException for null generator");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testValidGeneratorSetsArray() {
        final int length = 5;
        Integer[] array = new Integer[length];
        IntFunction<Integer> generator = new IntFunction<Integer>() {
            public Integer apply(int i) {
                return i * i;
            }
        };
        Arrays.parallelSetAll(array, generator);
        for (int i = 0; i < length; i++) {
            if (!Integer.valueOf(i * i).equals(array[i])) {
                throw new AssertionError("Array element at index " + i + " expected " + (i * i) + " but was " + array[i]);
            }
        }
    }
}
