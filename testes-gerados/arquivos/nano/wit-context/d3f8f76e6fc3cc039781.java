/*
 * @test
 * @summary Test for ImmutableCollections.remove method to verify exception throwing behavior.
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsUOE();
    }

    public static void testRemoveThrowsUOE() {
        ImmutableCollections.AbstractImmutableMap<String, String> map = new ImmutableCollections.AbstractImmutableMap<>();
        try {
            map.remove("key");
            System.out.println("Test failed: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: UnsupportedOperationException was thrown as expected.");
        } catch (Exception e) {
            System.out.println("Test failed: Unexpected exception type: " + e);
        }
    }
}
