/*
 * @test    @summary Test for Collections.addAll method to ensure it throws UnsupportedOperationException.
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testAddAllThrows();
    }

    public static void testAddAllThrows() {
        Collection<String> collection = new ArrayList<>();
        Collection<String> otherCollection = new ArrayList<>();
        otherCollection.add("item1");
        otherCollection.add("item2");
        try {
            boolean result = java.util.Collections.addAll(collection, "item1", "item2");
            // Since addAll throws UnsupportedOperationException, this line should not be reached.
            // If it is reached, the test fails.
            System.err.println("addAll did not throw as expected, result: " + result);
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes.
            System.out.println("addAll threw UnsupportedOperationException as expected.");
        } catch (Exception e) {
            // Unexpected exception, test fails.
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
