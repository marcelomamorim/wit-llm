/*
 * @test
 * @summary Test for ImmutableCollections.get(int index)
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testGetWithValidIndex();
        testGetWithNegativeIndex();
        testGetWithIndexEqualToSize();
    }

    private static void testGetWithValidIndex() {
        // Setup: create a list with known size
        java.util.List<Integer> list = java.util.List.of(10, 20, 30);
        // Use reflection to access the internal ImmutableCollections instance if needed
        // but here, we assume get() is accessible via a static method or similar
        // Since get() is an instance method, create a subclass or dummy object if needed
        // For simplicity, assume we can instantiate a minimal object with get() method
        // But as per the context, we test the method directly
        // So, create a subclass for testing purposes
        class TestImmutableCollections extends java.util.ImmutableCollections {
            public Integer get(int index) {
                return super.get(index);
            }
        }
        TestImmutableCollections obj = new TestImmutableCollections();
        // Mock or set internal state if needed; here, we assume get() works on list
        // For demonstration, directly test the method with a valid index
        try {
            Integer result = list.get(1);
            if (result != 20) {
                throw new AssertionError("Expected 20, got " + result);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("Unexpected exception for valid index", e);
        }
    }

    private static void testGetWithNegativeIndex() {
        java.util.List<Integer> list = java.util.List.of(10, 20, 30);
        try {
            list.get(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
    }

    private static void testGetWithIndexEqualToSize() {
        java.util.List<Integer> list = java.util.List.of(10, 20, 30);
        try {
            list.get(list.size());
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to size");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
    }
}
