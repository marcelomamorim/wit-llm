/*
 * @test
 * @summary Test java.util.Objects.checkIndex(int, int) method
 * @run main ObjectsWitupTest
 */

public class ObjectsWitupTest {
    public static void main(String[] args) {
        testValidIndex();
        testIndexEqualsLength();
        testNegativeIndex();
        testNegativeLength();
        testLengthZero();
    }

    private static void testValidIndex() {
        int length = 5;
        for (int i = 0; i < length; i++) {
            int result = java.util.Objects.checkIndex(i, length);
            if (result != i) {
                throw new AssertionError("checkIndex returned " + result + " but expected " + i);
            }
        }
    }

    private static void testIndexEqualsLength() {
        int length = 5;
        try {
            java.util.Objects.checkIndex(length, length);
            throw new AssertionError("Expected IndexOutOfBoundsException for index == length");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testNegativeIndex() {
        int length = 5;
        try {
            java.util.Objects.checkIndex(-1, length);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testNegativeLength() {
        int length = -1;
        try {
            java.util.Objects.checkIndex(0, length);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative length");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testLengthZero() {
        int length = 0;
        try {
            java.util.Objects.checkIndex(0, length);
            throw new AssertionError("Expected IndexOutOfBoundsException for index 0 with length 0");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
