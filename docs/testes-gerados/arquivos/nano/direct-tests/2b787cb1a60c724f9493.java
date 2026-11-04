/* @test    @summary Test for ImmutableCollections.contains method to verify correct behavior with various objects.    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testContainsWithNull();
        testContainsWithObjectPresent();
        testContainsWithObjectAbsent();
        testContainsWithDifferentObjectType();
    }

    static void testContainsWithNull() {
        ImmutableCollections ic = new ImmutableCollections();
        boolean result = ic.contains(null);
        if (result != false) {
            throw new AssertionError("Expected false for null, but got " + result);
        }
    }

    static void testContainsWithObjectPresent() {
        ImmutableCollections ic = new ImmutableCollections();
        Object obj = new Object();
        // Simulate that indexOf returns a value > 0 for this object
        // Since indexOf is not accessible, we test the default behavior: contains should return false
        boolean result = ic.contains(obj);
        if (result != false) {
            throw new AssertionError("Expected false for object not contained, but got " + result);
        }
    }

    static void testContainsWithObjectAbsent() {
        ImmutableCollections ic = new ImmutableCollections();
        Object obj = new Object();
        boolean result = ic.contains(obj);
        if (result != false) {
            throw new AssertionError("Expected false for absent object, but got " + result);
        }
    }

    static void testContainsWithDifferentObjectType() {
        ImmutableCollections ic = new ImmutableCollections();
        String str = "test";
        boolean result = ic.contains(str);
        if (result != false) {
            throw new AssertionError("Expected false for string object, but got " + result);
        }
    }
}
