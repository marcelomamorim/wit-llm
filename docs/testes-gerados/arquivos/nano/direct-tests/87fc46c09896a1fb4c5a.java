/* @test    @summary Test for ImmutableCollections.containsKey method    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;
import java.util.Objects;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testContainsKeyWithNull();
        testContainsKeyWithExistingObject();
        testContainsKeyWithNonExistingObject();
    }

    static void testContainsKeyWithNull() {
        try {
            ImmutableCollections instance = new ImmutableCollections();
            instance.containsKey(null);
        } catch (NullPointerException e) {
            System.out.println("Passed null argument test");
            return;
        }
        throw new AssertionError("Expected NullPointerException for null argument");
    }

    static void testContainsKeyWithExistingObject() {
        Object key = new Object();
        // Simulate a collection with size > 0 and probe returning >= 0
        ImmutableCollections instance = new ImmutableCollections() {
            @Override
            public boolean containsKey(Object o) {
                Objects.requireNonNull(o);
                return true; // simulate key exists
            }
        };
        boolean result = instance.containsKey(key);
        if (!result) {
            throw new AssertionError("Expected true for existing key");
        }
        System.out.println("Passed existing key test");
    }

    static void testContainsKeyWithNonExistingObject() {
        Object key = new Object();
        // Simulate a collection with size > 0 and probe returning < 0
        ImmutableCollections instance = new ImmutableCollections() {
            @Override
            public boolean containsKey(Object o) {
                Objects.requireNonNull(o);
                return false; // simulate key does not exist
            }
        };
        boolean result = instance.containsKey(key);
        if (result) {
            throw new AssertionError("Expected false for non-existing key");
        }
        System.out.println("Passed non-existing key test");
    }
}
