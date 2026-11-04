/*
 * @test
 * @summary Test java.util.Objects.checkFromToIndex method delegation and behavior
 * @run main ObjectsWitupTest
 */

public class ObjectsWitupTest {
    public static void main(String[] args) {
        // Test valid range: fromIndex == 0, toIndex == length
        int length = 10;
        int fromIndex = 0;
        int toIndex = length;
        int result = java.util.Objects.checkFromToIndex(fromIndex, toIndex, length);
        if (result != fromIndex) {
            throw new AssertionError("Expected checkFromToIndex to return fromIndex when valid");
        }

        // Test valid sub-range
        fromIndex = 3;
        toIndex = 7;
        result = java.util.Objects.checkFromToIndex(fromIndex, toIndex, length);
        if (result != fromIndex) {
            throw new AssertionError("Expected checkFromToIndex to return fromIndex when valid sub-range");
        }

        // Test fromIndex == toIndex (empty range) - should be valid
        fromIndex = 5;
        toIndex = 5;
        result = java.util.Objects.checkFromToIndex(fromIndex, toIndex, length);
        if (result != fromIndex) {
            throw new AssertionError("Expected checkFromToIndex to return fromIndex when fromIndex == toIndex");
        }

        // Test fromIndex == length and toIndex == length (empty range at end) - valid
        fromIndex = length;
        toIndex = length;
        result = java.util.Objects.checkFromToIndex(fromIndex, toIndex, length);
        if (result != fromIndex) {
            throw new AssertionError("Expected checkFromToIndex to return fromIndex when empty range at end");
        }

        // Test invalid: fromIndex < 0
        try {
            java.util.Objects.checkFromToIndex(-1, 5, length);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex < 0");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        // Test invalid: toIndex > length
        try {
            java.util.Objects.checkFromToIndex(0, length + 1, length);
            throw new AssertionError("Expected IndexOutOfBoundsException for toIndex > length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        // Test invalid: fromIndex > toIndex
        try {
            java.util.Objects.checkFromToIndex(7, 3, length);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex > toIndex");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
