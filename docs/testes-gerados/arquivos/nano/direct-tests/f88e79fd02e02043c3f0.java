/* @test    @summary Test for ImmutableCollections.contains(Object o) method    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testContainsWithEmptyCollection();
        testContainsWithNonEmptyCollection();
        testContainsWithNull();
    }

    static void testContainsWithEmptyCollection() {
        // Create an empty collection by calling a method that returns an empty list
        java.util.List<Object> emptyList = java.util.ImmutableCollections.listCopy(java.util.Collections.emptyList());
        // Since the collection is empty, contains should return false for any object
        boolean result = new java.util.ImmutableCollections().contains(new Object());
        if (result) {
            throw new AssertionError("Expected contains to return false for empty collection");
        }
    }

    static void testContainsWithNonEmptyCollection() {
        java.util.List<String> list = java.util.ImmutableCollections.listCopy(java.util.Arrays.asList("a", "b", "c"));
        java.util.ImmutableCollections collection = new java.util.ImmutableCollections();
        // Simulate the collection containing "a", "b", "c"
        // Since the actual internal state is not accessible, test behavior indirectly
        boolean containsA = list.contains("a");
        boolean containsD = list.contains("d");
        if (containsA != true || containsD != false) {
            throw new AssertionError("Unexpected contains result for list");
        }
    }

    static void testContainsWithNull() {
        try {
            new java.util.ImmutableCollections().contains(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
