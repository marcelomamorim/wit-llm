/* @test    @summary Test for Collections.get(int index) method to verify correct element retrieval and exception handling.    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testGetWithValidIndex();
        testGetWithInvalidIndex();
    }

    private static void testGetWithValidIndex() {
        // Setup: create a collection with a single element
        Object element = new Object();
        Object result = null;
        try {
            result = Collections.get(element, 0);
            if (result != element) {
                throw new AssertionError("Expected element to be returned.");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid index: " + e);
        }
    }

    private static void testGetWithInvalidIndex() {
        // Setup: create a collection with a single element
        Object element = new Object();
        try {
            Collections.get(element, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index 1.");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
