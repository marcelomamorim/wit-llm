/*
 * @test
 * @summary Test Collections.UnmodifiableCollection.removeIf throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.Predicate;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveIfUnsupportedOperationException();
    }

    private static void testRemoveIfUnsupportedOperationException() {
        // Create an unmodifiable collection wrapper around a simple collection
        java.util.Collection<String> base = new java.util.ArrayList<>();
        base.add("test");
        java.util.Collection<String> unmodifiable = Collections.unmodifiableCollection(base);

        Predicate<String> alwaysTrue = new Predicate<String>() {
            public boolean test(String s) {
                return true;
            }
        };

        boolean threw = false;
        try {
            unmodifiable.removeIf(alwaysTrue);
        } catch (UnsupportedOperationException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown by removeIf");
        }
    }
}
