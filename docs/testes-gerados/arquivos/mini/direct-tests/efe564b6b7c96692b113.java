/*
 * @test
 * @summary Test java.util.Arrays.parallelSetAll with various inputs
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntFunction;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testNullGenerator();
        testEmptyArray();
        testSimpleArray();
        testParallelSetAllConsistency();
    }

    private static void testNullGenerator() {
        String[] arr = new String[1];
        try {
            Arrays.parallelSetAll(arr, null);
            throw new RuntimeException("Expected NullPointerException for null generator");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testEmptyArray() {
        Integer[] arr = new Integer[0];
        Arrays.parallelSetAll(arr, new IntFunction<Integer>() {
            public Integer apply(int i) {
                throw new RuntimeException("Should not be called for empty array");
            }
        });
        if (arr.length != 0) {
            throw new RuntimeException("Array length changed unexpectedly");
        }
    }

    private static void testSimpleArray() {
        final int length = 10;
        Integer[] arr = new Integer[length];
        Arrays.parallelSetAll(arr, new IntFunction<Integer>() {
            public Integer apply(int i) {
                return i * i;
            }
        });
        for (int i = 0; i < length; i++) {
            if (!Objects.equals(arr[i], i * i)) {
                throw new RuntimeException("Element at index " + i + " expected " + (i * i) + " but was " + arr[i]);
            }
        }
    }

    private static void testParallelSetAllConsistency() {
        final int length = 1000;
        Integer[] arr1 = new Integer[length];
        Integer[] arr2 = new Integer[length];

        IntFunction<Integer> generator = new IntFunction<Integer>() {
            public Integer apply(int i) {
                return i + 1;
            }
        };

        Arrays.parallelSetAll(arr1, generator);
        Arrays.parallelSetAll(arr2, generator);

        for (int i = 0; i < length; i++) {
            if (!arr1[i].equals(arr2[i])) {
                throw new RuntimeException("Mismatch at index " + i + ": " + arr1[i] + " != " + arr2[i]);
            }
        }
    }
}
