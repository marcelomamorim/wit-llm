/*
 * @test
 * @summary Test java.util.Arrays.copyOfRange(U[] original, int from, int to, Class<? extends T[]> newType)
 * @run main java.util.ArraysWitupTest
 */

import java.util.Arrays;
import java.lang.reflect.Array;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testCopyOfRangeNormal();
        testCopyOfRangeEmptyRange();
        testCopyOfRangeFromEqualsTo();
        testCopyOfRangeFromZeroToLength();
        testCopyOfRangeNewTypeObjectArray();
        testCopyOfRangeThrowsOnNegativeLength();
    }

    private static void testCopyOfRangeNormal() {
        String[] original = {"a", "b", "c", "d", "e"};
        String[] copy = Arrays.copyOfRange(original, 1, 4, String[].class);
        if (copy.length != 3) throw new RuntimeException("Length mismatch");
        if (!"b".equals(copy[0])) throw new RuntimeException("Element 0 mismatch");
        if (!"c".equals(copy[1])) throw new RuntimeException("Element 1 mismatch");
        if (!"d".equals(copy[2])) throw new RuntimeException("Element 2 mismatch");
    }

    private static void testCopyOfRangeEmptyRange() {
        Integer[] original = {1, 2, 3};
        Integer[] copy = Arrays.copyOfRange(original, 2, 2, Integer[].class);
        if (copy.length != 0) throw new RuntimeException("Expected empty array");
    }

    private static void testCopyOfRangeFromEqualsTo() {
        Double[] original = {1.0, 2.0, 3.0};
        Double[] copy = Arrays.copyOfRange(original, 1, 1, Double[].class);
        if (copy.length != 0) throw new RuntimeException("Expected empty array when from == to");
    }

    private static void testCopyOfRangeFromZeroToLength() {
        Character[] original = {'x', 'y', 'z'};
        Character[] copy = Arrays.copyOfRange(original, 0, 3, Character[].class);
        if (copy.length != 3) throw new RuntimeException("Length mismatch");
        if (!copy[0].equals('x')) throw new RuntimeException("Element 0 mismatch");
        if (!copy[1].equals('y')) throw new RuntimeException("Element 1 mismatch");
        if (!copy[2].equals('z')) throw new RuntimeException("Element 2 mismatch");
    }

    private static void testCopyOfRangeNewTypeObjectArray() {
        String[] original = {"foo", "bar"};
        Object[] copy = Arrays.copyOfRange(original, 0, 2, Object[].class);
        if (copy.length != 2) throw new RuntimeException("Length mismatch");
        if (!"foo".equals(copy[0])) throw new RuntimeException("Element 0 mismatch");
        if (!"bar".equals(copy[1])) throw new RuntimeException("Element 1 mismatch");
    }

    private static void testCopyOfRangeThrowsOnNegativeLength() {
        boolean thrown = false;
        try {
            String[] original = {"a", "b"};
            Arrays.copyOfRange(original, 3, 2, String[].class);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) throw new RuntimeException("Expected IllegalArgumentException for from > to");
    }
}
