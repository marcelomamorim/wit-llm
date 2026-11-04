/*
 * @test
 * @summary Behavioral tests for Arrays.copyOfRange(int[], int, int)
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testBasicRange() {
        int[] result = java.util.Arrays.copyOfRange(new int[] {1, 2, 3, 4, 5}, 1, 4);
        check(java.util.Arrays.equals(result, new int[] {2, 3, 4}),
              "expected [2, 3, 4] but got " + java.util.Arrays.toString(result));
    }

    static void testEmptyRangeWhenFromEqualsTo() {
        int[] result = java.util.Arrays.copyOfRange(new int[] {1, 2, 3}, 2, 2);
        check(result.length == 0, "expected empty array but length was " + result.length);
    }

    static void testToBeyondLengthPadsWithZeros() {
        int[] result = java.util.Arrays.copyOfRange(new int[] {1, 2, 3}, 1, 6);
        check(java.util.Arrays.equals(result, new int[] {2, 3, 0, 0, 0}),
              "expected [2, 3, 0, 0, 0] but got " + java.util.Arrays.toString(result));
    }

    static void testFromEqualsLengthPadsWithZeros() {
        int[] result = java.util.Arrays.copyOfRange(new int[] {1, 2, 3}, 3, 5);
        check(java.util.Arrays.equals(result, new int[] {0, 0}),
              "expected [0, 0] but got " + java.util.Arrays.toString(result));
    }

    static void testToLessThanFromThrowsIllegalArgumentException() {
        try {
            java.util.Arrays.copyOfRange(new int[] {1, 2, 3, 4, 5}, 4, 2);
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            check("4 > 2".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
    }

    static void testFromBeyondLengthThrowsIndexException() {
        try {
            java.util.Arrays.copyOfRange(new int[] {1, 2, 3}, 5, 6);
            throw new RuntimeException("expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected: arraycopy rejects the negative length
        }
    }

    static void testReturnsNewArray() {
        int[] original = {1, 2, 3};
        int[] result = java.util.Arrays.copyOfRange(original, 0, 3);
        check(result != original, "result must be a new array");
        result[0] = 99;
        check(original[0] == 1, "original array must not be modified");
    }

    public static void main(String[] args) {
        testBasicRange();
        testEmptyRangeWhenFromEqualsTo();
        testToBeyondLengthPadsWithZeros();
        testFromEqualsLengthPadsWithZeros();
        testToLessThanFromThrowsIllegalArgumentException();
        testFromBeyondLengthThrowsIndexException();
        testReturnsNewArray();
    }
}
