/*
 * @test
 * @summary Verify Arrays.parallelSetAll(int[], IntUnaryOperator) fills elements by index and rejects null arguments
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testFillsEachIndexWithGeneratorResult();
        testEmptyArrayIsAccepted();
        testNullGeneratorThrowsNPE();
        testNullArrayThrowsNPE();
    }

    static void testFillsEachIndexWithGeneratorResult() {
        int[] array = new int[1000];
        Arrays.parallelSetAll(array, i -> i * 2);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != i * 2) {
                throw new AssertionError("index " + i + " expected " + (i * 2) + " but was " + array[i]);
            }
        }
    }

    static void testEmptyArrayIsAccepted() {
        int[] array = new int[0];
        Arrays.parallelSetAll(array, i -> 42);
        if (array.length != 0) {
            throw new AssertionError("empty array length changed");
        }
    }

    static void testNullGeneratorThrowsNPE() {
        int[] array = new int[5];
        try {
            Arrays.parallelSetAll(array, null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null generator");
    }

    static void testNullArrayThrowsNPE() {
        try {
            Arrays.parallelSetAll(null, i -> i);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null array");
    }
}
