/*
 * @test
 * @summary Test for Collections.get(int index) method, covering normal and exceptional cases.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testGetWithValidIndex();
        testGetWithInvalidIndex();
    }

    private static void testGetWithValidIndex() {
        try {
            // Setup: create a singleton list with one element
            java.util.List<String> singletonList = java.util.Collections.singletonList("element");
            // Call get(0), should return the element
            String result = Collections.get(0);
            if (!"element".equals(result)) {
                throw new AssertionError("Expected 'element', got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid index test: " + e);
        }
    }

    private static void testGetWithInvalidIndex() {
        try {
            // Setup: create a singleton list with one element
            java.util.List<String> singletonList = java.util.Collections.singletonList("element");
            // Call get(1), should throw IndexOutOfBoundsException
            Collections.get(1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index 1");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
        try {
            // Call get(-1), should throw IndexOutOfBoundsException
            Collections.get(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
