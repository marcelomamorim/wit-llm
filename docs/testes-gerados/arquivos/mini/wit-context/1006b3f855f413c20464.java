/*
 * @test
 * @summary Test java.util.Objects.checkFromIndexSize method for bounds checking
 * @run main ObjectsWitupTest
 */

public class ObjectsWitupTest {
    public static void main(String[] args) {
        testValidIndices();
        testInvalidIndices();
    }

    private static void testValidIndices() {
        // Valid cases: fromIndex and size within length bounds
        int length = 10;
        int fromIndex = 0;
        int size = 10;
        int result = java.util.Objects.checkFromIndexSize(fromIndex, size, length);
        if (result != fromIndex) {
            throw new AssertionError("Expected return value to be fromIndex when valid");
        }

        fromIndex = 5;
        size = 5;
        result = java.util.Objects.checkFromIndexSize(fromIndex, size, length);
        if (result != fromIndex) {
            throw new AssertionError("Expected return value to be fromIndex when valid");
        }

        fromIndex = 0;
        size = 0;
        result = java.util.Objects.checkFromIndexSize(fromIndex, size, length);
        if (result != fromIndex) {
            throw new AssertionError("Expected return value to be fromIndex when size is zero");
        }
    }

    private static void testInvalidIndices() {
        int length = 10;

        // fromIndex negative
        try {
            java.util.Objects.checkFromIndexSize(-1, 5, length);
            throw new AssertionError("Expected exception for negative fromIndex");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        // size negative
        try {
            java.util.Objects.checkFromIndexSize(0, -1, length);
            throw new AssertionError("Expected exception for negative size");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        // length negative
        try {
            java.util.Objects.checkFromIndexSize(0, 0, -1);
            throw new AssertionError("Expected exception for negative length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        // size too large
        try {
            java.util.Objects.checkFromIndexSize(5, 6, length); // 5 + 6 = 11 > 10
            throw new AssertionError("Expected exception for size too large");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        // fromIndex too large
        try {
            java.util.Objects.checkFromIndexSize(11, 0, length); // fromIndex > length
            throw new AssertionError("Expected exception for fromIndex too large");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
