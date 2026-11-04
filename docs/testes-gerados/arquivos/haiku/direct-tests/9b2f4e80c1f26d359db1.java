/*
 * @test
 * @summary Behavioral tests for Arrays.copyOfRange(char[], int, int)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testCopiesRequestedRange();
        testPadsWithZeroBeyondOriginalLength();
        testEmptyRangeReturnsEmptyArray();
        testFromEqualToLengthReturnsZeros();
        testReversedRangeThrowsIllegalArgument();
        testFromBeyondLengthThrowsIndexException();
        testNegativeFromThrowsIndexException();
        testResultIsIndependentCopy();
    }

    static void testCopiesRequestedRange() {
        char[] src = {'a', 'b', 'c', 'd', 'e'};
        char[] r = Arrays.copyOfRange(src, 1, 4);
        check(r.length == 3, "length expected 3");
        check(r[0] == 'b' && r[1] == 'c' && r[2] == 'd', "content mismatch");
    }

    static void testPadsWithZeroBeyondOriginalLength() {
        char[] src = {'x', 'y'};
        char[] r = Arrays.copyOfRange(src, 1, 4);
        check(r.length == 3, "length expected 3");
        check(r[0] == 'y', "first element expected 'y'");
        check(r[1] == '\u0000' && r[2] == '\u0000', "padding must be zero");
    }

    static void testEmptyRangeReturnsEmptyArray() {
        char[] src = {'a', 'b', 'c'};
        char[] r = Arrays.copyOfRange(src, 2, 2);
        check(r.length == 0, "empty range expected length 0");
    }

    static void testFromEqualToLengthReturnsZeros() {
        char[] src = {'a', 'b'};
        char[] r = Arrays.copyOfRange(src, 2, 4);
        check(r.length == 2, "length expected 2");
        check(r[0] == '\u0000' && r[1] == '\u0000', "expected zero padding");
    }

    static void testReversedRangeThrowsIllegalArgument() {
        char[] src = {'a', 'b', 'c', 'd', 'e'};
        try {
            Arrays.copyOfRange(src, 5, 3);
            throw new RuntimeException("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            check("5 > 3".equals(e.getMessage()), "unexpected message: " + e.getMessage());
        }
    }

    static void testFromBeyondLengthThrowsIndexException() {
        char[] src = {'a', 'b'};
        try {
            Arrays.copyOfRange(src, 5, 7);
            throw new RuntimeException("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException e) {
            // expected: arraycopy with negative length
        }
    }

    static void testNegativeFromThrowsIndexException() {
        char[] src = {'a', 'b', 'c'};
        try {
            Arrays.copyOfRange(src, -1, 2);
            throw new RuntimeException("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException e) {
            // expected: arraycopy with negative source position
        }
    }

    static void testResultIsIndependentCopy() {
        char[] src = {'a', 'b', 'c'};
        char[] r = Arrays.copyOfRange(src, 0, 3);
        r[0] = 'z';
        check(src[0] == 'a', "original must not be modified");
        check(r != src, "result must be a new array");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
