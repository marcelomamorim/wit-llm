/*
 * @test
 * @summary Test java.util.Objects.checkFromToIndex for bounds checking
 * @run main ObjectsWitupTest
 */

public class ObjectsWitupTest {
    public static void main(String[] args) {
        testValidIndices();
        testFromIndexGreaterThanToIndex();
        testFromIndexNegative();
        testToIndexGreaterThanLength();
    }

    private static void testValidIndices() {
        // Valid range: fromIndex <= toIndex <= length, fromIndex >= 0
        int fromIndex = 0;
        int toIndex = 5;
        int length = 10;
        int result = java.util.Objects.checkFromToIndex(fromIndex, toIndex, length);
        if (result != fromIndex) {
            throw new AssertionError("Expected return value to be fromIndex: " + fromIndex + ", but got: " + result);
        }
    }

    private static void testFromIndexGreaterThanToIndex() {
        int fromIndex = 6;
        int toIndex = 5;
        int length = 10;
        try {
            java.util.Objects.checkFromToIndex(fromIndex, toIndex, length);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex > toIndex");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
    }

    private static void testFromIndexNegative() {
        int fromIndex = -1;
        int toIndex = 5;
        int length = 10;
        try {
            java.util.Objects.checkFromToIndex(fromIndex, toIndex, length);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative fromIndex");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
    }

    private static void testToIndexGreaterThanLength() {
        int fromIndex = 0;
        int toIndex = 11;
        int length = 10;
        try {
            java.util.Objects.checkFromToIndex(fromIndex, toIndex, length);
            throw new AssertionError("Expected IndexOutOfBoundsException for toIndex > length");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
    }
}
