/* @test    @summary Test for Arrays.copyOfRange(byte[] original, int from, int to) method.    @run main ArraysWitupTest */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testCopyOfRangeNormal();
        testCopyOfRangeFromEqualsTo();
        testCopyOfRangeFromGreaterThanTo();
        testCopyOfRangeNegativeFrom();
        testCopyOfRangeNegativeTo();
        testCopyOfRangeFromExceedsLength();
        testCopyOfRangeToExceedsLength();
    }

    static void testCopyOfRangeNormal() {
        byte[] original = {1, 2, 3, 4, 5};
        byte[] result = Arrays.copyOfRange(original, 1, 4);
        byte[] expected = {2, 3, 4};
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("Failed testCopyOfRangeNormal: " + Arrays.toString(result));
        }
    }

    static void testCopyOfRangeFromEqualsTo() {
        byte[] original = {1, 2, 3};
        byte[] result = Arrays.copyOfRange(original, 2, 2);
        byte[] expected = {};
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("Failed testCopyOfRangeFromEqualsTo: " + Arrays.toString(result));
        }
    }

    static void testCopyOfRangeFromGreaterThanTo() {
        byte[] original = {1, 2, 3};
        boolean exceptionThrown = false;
        try {
            Arrays.copyOfRange(original, 3, 2);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Failed testCopyOfRangeFromGreaterThanTo: exception not thrown");
        }
    }

    static void testCopyOfRangeNegativeFrom() {
        byte[] original = {1, 2, 3};
        byte[] result = Arrays.copyOfRange(original, -1, 2);
        byte[] expected = {1, 2};
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("Failed testCopyOfRangeNegativeFrom: " + Arrays.toString(result));
        }
    }

    static void testCopyOfRangeNegativeTo() {
        byte[] original = {1, 2, 3};
        byte[] result = Arrays.copyOfRange(original, 0, -1);
        boolean exceptionThrown = false;
        try {
            Arrays.copyOfRange(original, 0, -1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Failed testCopyOfRangeNegativeTo: exception not thrown");
        }
    }

    static void testCopyOfRangeFromExceedsLength() {
        byte[] original = {1, 2, 3};
        byte[] result = Arrays.copyOfRange(original, 4, 5);
        byte[] expected = {};
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("Failed testCopyOfRangeFromExceedsLength: " + Arrays.toString(result));
        }
    }

    static void testCopyOfRangeToExceedsLength() {
        byte[] original = {1, 2, 3};
        byte[] result = Arrays.copyOfRange(original, 1, 10);
        byte[] expected = {2, 3};
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("Failed testCopyOfRangeToExceedsLength: " + Arrays.toString(result));
        }
    }
}
