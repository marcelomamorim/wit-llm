/*
 * @test
 * @summary Test for java.util.ImmutableCollections.contains(Object o)
 * @run main ImmutableCollectionsContainsTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsContainsTest {

    public static void main(String[] args) {
        testContainsWithNull();
        testContainsWithNonNull();
    }

    static void testContainsWithNull() {
        ImmutableCollections collection = new ImmutableCollections();
        boolean threwException = false;
        try {
            collection.contains(null);
        } catch (NullPointerException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected NullPointerException when calling contains(null)");
        }
    }

    static void testContainsWithNonNull() {
        ImmutableCollections collection = new ImmutableCollections();
        Object obj = new Object();
        boolean result = false;
        try {
            result = collection.contains(obj);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
        // Since the collection is empty, contains should return false
        if (result != false) {
            throw new AssertionError("Expected false for contains on empty collection");
        }
    }
}
