/*
 * @test
 * @summary Test java.util.Arrays.copyOfRange(T[] original, int from, int to)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testCopyOfRangeNormal();
        testCopyOfRangeEmptyRange();
        testCopyOfRangeFromEqualsTo();
        testCopyOfRangeFromGreaterThanTo();
        testCopyOfRangeFromNegative();
        testCopyOfRangeToGreaterThanLength();
        testCopyOfRangeNullOriginal();
    }

    private static void testCopyOfRangeNormal() {
        Integer[] original = {1, 2, 3, 4, 5};
        Integer[] copy = Arrays.copyOfRange(original, 1, 4);
        if (copy.length != 3) throw new RuntimeException("Length mismatch");
        if (copy[0] != 2 || copy[1] != 3 || copy[2] != 4) throw new RuntimeException("Content mismatch");
    }

    private static void testCopyOfRangeEmptyRange() {
        Integer[] original = {1, 2, 3};
        Integer[] copy = Arrays.copyOfRange(original, 2, 2);
        if (copy.length != 0) throw new RuntimeException("Expected empty array");
    }

    private static void testCopyOfRangeFromEqualsTo() {
        String[] original = {"a", "b", "c"};
        String[] copy = Arrays.copyOfRange(original, 1, 1);
        if (copy.length != 0) throw new RuntimeException("Expected empty array when from == to");
    }

    private static void testCopyOfRangeFromGreaterThanTo() {
        try {
            Integer[] original = {1, 2, 3};
            Arrays.copyOfRange(original, 2, 1);
            throw new RuntimeException("Expected IllegalArgumentException for from > to");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testCopyOfRangeFromNegative() {
        try {
            Integer[] original = {1, 2, 3};
            Arrays.copyOfRange(original, -1, 2);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative from");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCopyOfRangeToGreaterThanLength() {
        Integer[] original = {1, 2, 3};
        Integer[] copy = Arrays.copyOfRange(original, 1, 5);
        if (copy.length != 4) throw new RuntimeException("Length mismatch when to > original.length");
        if (copy[0] != 2 || copy[1] != 3) throw new RuntimeException("Content mismatch for valid elements");
        if (copy[2] != null || copy[3] != null) throw new RuntimeException("Expected null padding for out-of-bound elements");
    }

    private static void testCopyOfRangeNullOriginal() {
        try {
            Arrays.copyOfRange(null, 0, 1);
            throw new RuntimeException("Expected NullPointerException for null original");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
