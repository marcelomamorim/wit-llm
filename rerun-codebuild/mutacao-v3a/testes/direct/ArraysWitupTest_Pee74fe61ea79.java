/* @test    @summary Test java.util.Arrays.copyOfRange for various scenarios    @run main ArraysWitupTest_Pee74fe61ea79 */

import java.util.Arrays;

public class ArraysWitupTest_Pee74fe61ea79 {

    public static void main(String[] args) {
        testCopyOfRange_NormalCase();
        testCopyOfRange_FromEqualsTo();
        testCopyOfRange_FromGreaterThanTo();
        testCopyOfRange_FromNegative();
        testCopyOfRange_ToExceedsLength();
    }

    static void testCopyOfRange_NormalCase() {
        short[] original = {1, 2, 3, 4, 5};
        short[] result = Arrays.copyOfRange(original, 1, 4);
        assert result.length == 3 : "Expected length 3, got " + result.length;
        assert result[0] == 2 : "Expected 2, got " + result[0];
        assert result[1] == 3 : "Expected 3, got " + result[1];
        assert result[2] == 4 : "Expected 4, got " + result[2];
    }

    static void testCopyOfRange_FromEqualsTo() {
        short[] original = {10, 20, 30};
        short[] result = Arrays.copyOfRange(original, 2, 2);
        assert result.length == 0 : "Expected length 0, got " + result.length;
    }

    static void testCopyOfRange_FromGreaterThanTo() {
        short[] original = {1, 2, 3};
        try {
            Arrays.copyOfRange(original, 3, 2);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("3 > 2") : "Unexpected exception message: " + e.getMessage();
        }
    }

    static void testCopyOfRange_FromNegative() {
        short[] original = {5, 6, 7};
        try {
            Arrays.copyOfRange(original, -1, 2);
            // No explicit exception expected, as arraycopy handles negative index by throwing ArrayIndexOutOfBoundsException
            // but copyOfRange itself does not explicitly check for negative, relying on arraycopy
            // So, test that an exception is thrown
            assert false : "Expected ArrayIndexOutOfBoundsException";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCopyOfRange_ToExceedsLength() {
        short[] original = {8, 9};
        short[] result = Arrays.copyOfRange(original, 0, 5);
        assert result.length == 5 : "Expected length 5, got " + result.length;
        assert result[0] == 8 : "Expected 8, got " + result[0];
        assert result[1] == 9 : "Expected 9, got " + result[1];
        assert result[2] == 0 : "Expected 0, got " + result[2];
        assert result[3] == 0 : "Expected 0, got " + result[3];
        assert result[4] == 0 : "Expected 0, got " + result[4];
    }
}
