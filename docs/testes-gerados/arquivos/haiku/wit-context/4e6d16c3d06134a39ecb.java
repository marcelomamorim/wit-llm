/*
 * @test
 * @summary Witup-derived checks for Arrays.binarySearch(char[], char)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyArray() {
        check(Arrays.binarySearch(new char[0], 'a') == -1, "empty array should return -1");
    }

    static void testKeyFound() {
        char[] a = {'a', 'c', 'e'};
        check(Arrays.binarySearch(a, 'a') == 0, "first element index");
        check(Arrays.binarySearch(a, 'c') == 1, "middle element index");
        check(Arrays.binarySearch(a, 'e') == 2, "last element index");
    }

    static void testKeyBeforeAllElements() {
        char[] a = {'b', 'd'};
        check(Arrays.binarySearch(a, 'a') == -1, "key smaller than all elements");
    }

    static void testKeyAfterAllElements() {
        char[] a = {'b', 'd'};
        check(Arrays.binarySearch(a, 'z') == -3, "key greater than all elements");
    }

    static void testKeyInGap() {
        char[] a = {'a', 'd'};
        check(Arrays.binarySearch(a, 'c') == -2, "insertion point for missing key");
    }

    static void testMatchesRangeOverload() {
        char[] a = {'a', 'b', 'k', 'm', 'x'};
        char[] keys = {'a', 'b', 'c', 'k', 'm', 'x', 'z'};
        for (char k : keys) {
            check(Arrays.binarySearch(a, k) == Arrays.binarySearch(a, 0, a.length, k),
                  "whole-array search must match explicit full range for key " + k);
        }
    }

    static void testNullArrayThrows() {
        try {
            Arrays.binarySearch((char[]) null, 'a');
            throw new RuntimeException("expected NullPointerException for null array");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        testEmptyArray();
        testKeyFound();
        testKeyBeforeAllElements();
        testKeyAfterAllElements();
        testKeyInGap();
        testMatchesRangeOverload();
        testNullArrayThrows();
    }
}
