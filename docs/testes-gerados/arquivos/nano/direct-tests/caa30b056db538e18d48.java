/* @test    @summary Test for Collections.get(int index) method to verify correct element retrieval and exception handling.    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testGetValidIndex();
        testGetNegativeIndex();
        testGetIndexEqualSize();
        testGetIndexGreaterThanSize();
    }

    private static void testGetValidIndex() {
        try {
            String element = "test";
            // Create a list with a single element
            java.util.List<String> list = java.util.Collections.singletonList(element);
            String result = Collections.get(0);
            if (!result.equals(element)) {
                throw new AssertionError("Expected: " + element + ", but got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testGetValidIndex: " + e);
        }
    }

    private static void testGetNegativeIndex() {
        try {
            // Create a list with a single element
            java.util.List<String> list = java.util.Collections.singletonList("test");
            // Attempt to get element at negative index
            Collections.get(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testGetNegativeIndex: " + e);
        }
    }

    private static void testGetIndexEqualSize() {
        try {
            java.util.List<String> list = java.util.Collections.singletonList("test");
            // Attempt to get element at index equal to size
            Collections.get(1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to size");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testGetIndexEqualSize: " + e);
        }
    }

    private static void testGetIndexGreaterThanSize() {
        try {
            java.util.List<String> list = java.util.Collections.singletonList("test");
            // Attempt to get element at index greater than size
            Collections.get(2);
            throw new AssertionError("Expected IndexOutOfBoundsException for index greater than size");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testGetIndexGreaterThanSize: " + e);
        }
    }
}
