/*
 * @test
 * @summary Test for ImmutableCollections.putAll method to verify exception throwing behavior.
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.*;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testPutAllThrowsUOE();
    }

    public static void testPutAllThrowsUOE() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        try {
            ImmutableCollections.putAll(map);
            // If no exception, test fails
            System.err.println("Failed: Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            System.out.println("Passed: UnsupportedOperationException thrown as expected.");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
            System.exit(1);
        }
    }
}
