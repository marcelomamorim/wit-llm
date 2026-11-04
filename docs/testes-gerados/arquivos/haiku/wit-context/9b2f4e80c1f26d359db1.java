/*
 * @test
 * @summary Witup-derived tests for Arrays.copyOfRange(char[], int, int) exception and copy behavior
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {

    public static void main(String[] args) {
        testRangeIsCopied();
        testFromEqualsToGivesEmptyArray();
        testRangeBeyondOriginalIsPadded();
        testToLessThanFromThrowsIAE();
        testNegativeLengthThrowsIAE();
        testNullOriginalThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRangeIsCopied() {
        char[] original = {'a', 'b', 'c'};
        char[] copy = java.util.Arrays.copyOfRange(original, 0, 3);
        check(copy.length == 3, "length expected 3");
        check(copy[0] == 'a' && copy[1] == 'b' && copy[2] == 'c', "contents mismatch");
        check(copy != original, "must return a new array");
    }

    static void testFromEqualsToGivesEmptyArray() {
        char[] copy = java.util.Arrays.copyOfRange(new char[]{'x'}, 1, 1);
        check(copy.length == 0, "expected empty array when from == to");
    }

    static void testRangeBeyondOriginalIsPadded() {
        char[] copy = java.util.Arrays.copyOfRange(new char[]{'a', 'b'}, 1, 4);
        check(copy.length == 3, "length expected 3");
        check(copy[0] == 'b', "first element expected b");
        check(copy[1] == (char) 0 && copy[2] == (char) 0, "padding expected to be zero chars");
    }

    static void testToLessThanFromThrowsIAE() {
        boolean thrown = false;
        try {
            java.util.Arrays.copyOfRange(new char[]{'a', 'b', 'c'}, 2, 1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "IllegalArgumentException expected when to < from");
    }

    static void testNegativeLengthThrowsIAE() {
        boolean thrown = false;
        try {
            java.util.Arrays.copyOfRange(new char[]{'a'}, 5, 2);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "IllegalArgumentException expected for negative new length");
    }

    static void testNullOriginalThrowsNPE() {
        boolean thrown = false;
        try {
            java.util.Arrays.copyOfRange((char[]) null, 0, 1);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "NullPointerException expected for null original array");
    }
}
