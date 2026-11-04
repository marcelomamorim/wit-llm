/* @test    @summary Test for ImmutableCollections.get(Object o) method to verify behavior with null input and empty collection    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testGetWithNullObject();
        testGetWithEmptyCollection();
    }

    private static void testGetWithNullObject() {
        ImmutableCollections collection = new ImmutableCollections();
        try {
            collection.get(null);
            System.out.println("testGetWithNullObject: Failed - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testGetWithNullObject: Passed");
        } catch (Exception e) {
            System.out.println("testGetWithNullObject: Failed - Unexpected exception: " + e);
        }
    }

    private static void testGetWithEmptyCollection() {
        ImmutableCollections collection = new ImmutableCollections();
        // Simulate empty collection by setting size to 0 if possible, or assume collection is empty
        // Since size is not accessible, we assume collection is empty by default
        try {
            Object result = collection.get(new Object());
            if (result == null) {
                System.out.println("testGetWithEmptyCollection: Passed");
            } else {
                System.out.println("testGetWithEmptyCollection: Failed - Expected null, got: " + result);
            }
        } catch (Exception e) {
            System.out.println("testGetWithEmptyCollection: Failed - Unexpected exception: " + e);
        }
    }
}
