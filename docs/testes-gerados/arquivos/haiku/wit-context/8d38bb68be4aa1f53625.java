/*
 * @test
 * @summary Behavioural tests for Arrays.equals(byte[], byte[]) covering null handling, identity, length and element comparison
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testNullArguments();
        testSameReference();
        testEmptyArrays();
        testEqualContent();
        testEqualContentWithNegativeValues();
        testDifferentLength();
        testDifferentFirstElement();
        testDifferentLastElement();
        testSymmetry();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullArguments() {
        byte[] a = {1, 2, 3};
        check(Arrays.equals(null, null), "null, null should be equal");
        check(!Arrays.equals(a, null), "non-null vs null should not be equal");
        check(!Arrays.equals(null, a), "null vs non-null should not be equal");
    }

    static void testSameReference() {
        byte[] a = {5, 6, 7};
        check(Arrays.equals(a, a), "same reference should be equal");
    }

    static void testEmptyArrays() {
        check(Arrays.equals(new byte[0], new byte[0]), "two empty arrays should be equal");
    }

    static void testEqualContent() {
        byte[] a = {1, 2, 3, 4};
        byte[] b = {1, 2, 3, 4};
        check(Arrays.equals(a, b), "arrays with same content should be equal");
    }

    static void testEqualContentWithNegativeValues() {
        byte[] a = {Byte.MIN_VALUE, -1, 0, Byte.MAX_VALUE};
        byte[] b = {Byte.MIN_VALUE, -1, 0, Byte.MAX_VALUE};
        check(Arrays.equals(a, b), "arrays with boundary and negative values should be equal");
    }

    static void testDifferentLength() {
        byte[] a = {1, 2, 3};
        byte[] prefix = {1, 2};
        byte[] longer = {1, 2, 3, 0};
        check(!Arrays.equals(a, prefix), "shorter array with equal prefix should not be equal");
        check(!Arrays.equals(prefix, a), "symmetric shorter-prefix check failed");
        check(!Arrays.equals(a, longer), "longer array with equal prefix should not be equal");
        check(!Arrays.equals(longer, a), "symmetric longer-array check failed");
        check(!Arrays.equals(new byte[0], new byte[1]), "empty vs one-element should not be equal");
    }

    static void testDifferentFirstElement() {
        byte[] a = {1, 2, 3};
        byte[] b = {9, 2, 3};
        check(!Arrays.equals(a, b), "arrays differing in first element should not be equal");
    }

    static void testDifferentLastElement() {
        byte[] a = {1, 2, 3};
        byte[] b = {1, 2, 4};
        check(!Arrays.equals(a, b), "arrays differing in last element should not be equal");
    }

    static void testSymmetry() {
        byte[] a = {10, 20, 30};
        byte[] b = {10, 20, 31};
        check(Arrays.equals(a, a) == Arrays.equals(a, a), "equals should be deterministic");
        check(Arrays.equals(a, b) == Arrays.equals(b, a), "equals should be symmetric");
    }
}
