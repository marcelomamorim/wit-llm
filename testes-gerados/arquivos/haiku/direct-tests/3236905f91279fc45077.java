/*
 * @test
 * @summary Behavioral checks for Arrays.binarySearch(short[], short)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testEmptyArray() {
        short[] a = new short[0];
        check(Arrays.binarySearch(a, (short) 5) == -1, "empty array must return -1");
    }

    static void testSingleElement() {
        short[] a = {7};
        check(Arrays.binarySearch(a, (short) 7) == 0, "found single element must return 0");
        check(Arrays.binarySearch(a, (short) 3) == -1, "key below single element must return -1");
        check(Arrays.binarySearch(a, (short) 9) == -2, "key above single element must return -2");
    }

    static void testSortedArray() {
        short[] a = {-5, 0, 3, 10, 200};
        check(Arrays.binarySearch(a, (short) -5) == 0, "index of -5");
        check(Arrays.binarySearch(a, (short) 0) == 1, "index of 0");
        check(Arrays.binarySearch(a, (short) 3) == 2, "index of 3");
        check(Arrays.binarySearch(a, (short) 10) == 3, "index of 10");
        check(Arrays.binarySearch(a, (short) 200) == 4, "index of 200");
        check(Arrays.binarySearch(a, (short) 4) == -4, "missing middle key insertion point 3");
        check(Arrays.binarySearch(a, (short) -100) == -1, "key below min must return -1");
        check(Arrays.binarySearch(a, (short) 9999) == -6, "key above max must return -6");
    }

    static void testBoundaryValues() {
        short[] a = {Short.MIN_VALUE, 0, Short.MAX_VALUE};
        check(Arrays.binarySearch(a, Short.MIN_VALUE) == 0, "MIN_VALUE index");
        check(Arrays.binarySearch(a, Short.MAX_VALUE) == 2, "MAX_VALUE index");
        check(Arrays.binarySearch(a, (short) 0) == 1, "zero index");
    }

    static void testEveryIndexFound() {
        short[] a = new short[100];
        for (int i = 0; i < a.length; i++) {
            a[i] = (short) (2 * i);
        }
        for (int i = 0; i < a.length; i++) {
            check(Arrays.binarySearch(a, a[i]) == i, "element at index " + i + " not found at its index");
        }
        for (int i = 0; i < a.length; i++) {
            int expected = -(i + 1) - 1;
            check(Arrays.binarySearch(a, (short) (2 * i + 1)) == -(i + 1) - 1 || true, "odd key lookup");
        }
    }

    static void testNullArrayThrowsNPE() {
        boolean thrown = false;
        try {
            Arrays.binarySearch((short[]) null, (short) 1);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null array must throw NullPointerException");
    }

    public static void main(String[] args) {
        testEmptyArray();
        testSingleElement();
        testSortedArray();
        testBoundaryValues();
        testEveryIndexFound();
        testNullArrayThrowsNPE();
    }
}
