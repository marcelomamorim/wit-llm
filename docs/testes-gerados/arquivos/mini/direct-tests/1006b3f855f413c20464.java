/*
 * @test
 * @summary Test java.util.Objects.checkFromIndexSize method delegating to Preconditions.checkFromIndexSize
 * @run main java.util.ObjectsWitupTest
 */

public class ObjectsWitupTest {
    public static void main(String[] args) {
        testValidRanges();
        testInvalidRanges();
    }

    private static void testValidRanges() {
        // Valid cases: fromIndex and size within length bounds
        int length = 10;

        // fromIndex=0, size=0
        int result = java.util.Objects.checkFromIndexSize(0, 0, length);
        if (result != 0) {
            throw new AssertionError("Expected 0 but got " + result);
        }

        // fromIndex=0, size=length
        result = java.util.Objects.checkFromIndexSize(0, length, length);
        if (result != 0) {
            throw new AssertionError("Expected 0 but got " + result);
        }

        // fromIndex=5, size=5
        result = java.util.Objects.checkFromIndexSize(5, 5, length);
        if (result != 5) {
            throw new AssertionError("Expected 5 but got " + result);
        }

        // fromIndex=length, size=0
        result = java.util.Objects.checkFromIndexSize(length, 0, length);
        if (result != length) {
            throw new AssertionError("Expected " + length + " but got " + result);
        }
    }

    private static void testInvalidRanges() {
        int length = 10;

        // fromIndex < 0
        try {
            java.util.Objects.checkFromIndexSize(-1, 0, length);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex < 0");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        // size < 0
        try {
            java.util.Objects.checkFromIndexSize(0, -1, length);
            throw new AssertionError("Expected IndexOutOfBoundsException for size < 0");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        // fromIndex > length
        try {
            java.util.Objects.checkFromIndexSize(length + 1, 0, length);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex > length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        // fromIndex + size > length
        try {
            java.util.Objects.checkFromIndexSize(5, 6, length);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex + size > length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
