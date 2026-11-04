/*
 * @test
 * @summary Test Collections.CopiesList.toArray() method for correct array creation and fill behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Arrays;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testToArrayWithNullElement();
        testToArrayWithNonNullElement();
    }

    private static void testToArrayWithNullElement() {
        // Create a CopiesList with n=3 and element=null
        // CopiesList is a private static class inside Collections, so we cannot instantiate it directly.
        // Instead, use Collections.nCopies to get a CopiesList instance.
        List<String> copiesList = Collections.nCopies(3, null);

        Object[] array = copiesList.toArray();

        if (array.length != 3) {
            throw new AssertionError("Expected array length 3, got " + array.length);
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                throw new AssertionError("Expected null at index " + i + ", got " + array[i]);
            }
        }
    }

    private static void testToArrayWithNonNullElement() {
        // Create a CopiesList with n=4 and element="x"
        List<String> copiesList = Collections.nCopies(4, "x");

        Object[] array = copiesList.toArray();

        if (array.length != 4) {
            throw new AssertionError("Expected array length 4, got " + array.length);
        }

        for (int i = 0; i < array.length; i++) {
            if (!"x".equals(array[i])) {
                throw new AssertionError("Expected 'x' at index " + i + ", got " + array[i]);
            }
        }
    }
}
