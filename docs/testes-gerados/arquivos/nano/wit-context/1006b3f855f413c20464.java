/*
 * @test
 * @summary Tests for java.util.Objects.checkFromIndexSize method.
 * @run main ObjectsWitupTest
 */

import java.util.Objects;

public class ObjectsWitupTest {

    public static void main(String[] args) {
        testCheckFromIndexSizeValid();
        testCheckFromIndexSizeNegativeValues();
        testCheckFromIndexSizeOutOfBounds();
    }

    static void testCheckFromIndexSizeValid() {
        int fromIndex = 2;
        int size = 3;
        int length = 10;
        try {
            int result = Objects.checkFromIndexSize(fromIndex, size, length);
            if (result != fromIndex) {
                throw new AssertionError("Expected result to be " + fromIndex + " but was " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid inputs", e);
        }
    }

    static void testCheckFromIndexSizeNegativeValues() {
        int[] testValues = {-1, -5, -10};
        for (int val : testValues) {
            try {
                Objects.checkFromIndexSize(val, 1, 10);
                throw new AssertionError("Expected exception for fromIndex = " + val);
            } catch (IndexOutOfBoundsException e) {
                // Expected exception
            }
            try {
                Objects.checkFromIndexSize(0, val, 10);
                throw new AssertionError("Expected exception for size = " + val);
            } catch (IndexOutOfBoundsException e) {
                // Expected exception
            }
            try {
                Objects.checkFromIndexSize(0, 1, val);
                throw new AssertionError("Expected exception for length = " + val);
            } catch (IndexOutOfBoundsException e) {
                // Expected exception
            }
        }
    }

    static void testCheckFromIndexSizeOutOfBounds() {
        try {
            Objects.checkFromIndexSize(5, 10, 10);
            throw new AssertionError("Expected exception for fromIndex=5, size=10, length=10");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
        try {
            Objects.checkFromIndexSize(0, 11, 10);
            throw new AssertionError("Expected exception for fromIndex=0, size=11, length=10");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
        try {
            Objects.checkFromIndexSize(8, 3, 10);
            throw new AssertionError("Expected exception for fromIndex=8, size=3, length=10");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
