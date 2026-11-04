/*
 * @test
 * @summary Test for Collections.set method to verify exception throwing behavior.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testSetThrowsUnsupportedOperationException();
    }

    private static void testSetThrowsUnsupportedOperationException() {
        try {
            // Prepare a list that is unmodifiable, e.g., via Collections.unmodifiableList
            java.util.List<Integer> list = java.util.Collections.unmodifiableList(java.util.Arrays.asList(1, 2, 3));
            // Attempt to set an element, expecting UnsupportedOperationException
            list.set(0, 99);
            // If no exception, the test fails
            System.err.println("FAIL: Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            System.out.println("PASS: UnsupportedOperationException thrown as expected.");
        } catch (Exception e) {
            // Unexpected exception, test fails
            System.err.println("FAIL: Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
