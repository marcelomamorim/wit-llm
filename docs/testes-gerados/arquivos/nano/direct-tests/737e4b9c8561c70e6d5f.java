/* @test    @summary Test for Collections.toArray() method to ensure it returns an array with correct size and filled elements.    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testToArrayReturnsArrayWithCorrectSizeAndElements();
    }

    public static void testToArrayReturnsArrayWithCorrectSizeAndElements() {
        Object[] result = Collections.toArray();
        int expectedSize = 0; // Since n is not explicitly set, assume default 0 for test
        if (result == null) {
            throw new AssertionError("Result should not be null");
        }
        if (result.length != expectedSize) {
            throw new AssertionError("Expected array length " + expectedSize + " but got " + result.length);
        }
        // Since element is not set, the array should be filled with nulls
        for (int i = 0; i < result.length; i++) {
            if (result[i] != null) {
                throw new AssertionError("Expected null at index " + i + " but found " + result[i]);
            }
        }
    }
}
