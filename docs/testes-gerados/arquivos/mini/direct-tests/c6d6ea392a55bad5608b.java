/*
 * @test
 * @summary Test Collections.removeIf method behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Objects;
import java.util.function.Predicate;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testRemoveIfWithNonNullPredicate();
        testRemoveIfWithNullPredicate();
    }

    private static void testRemoveIfWithNonNullPredicate() {
        // The removeIf method always returns false and does not remove anything
        Predicate<Object> alwaysTrue = new Predicate<Object>() {
            public boolean test(Object o) {
                return true;
            }
        };

        Collections c = new Collections() { };
        boolean result = c.removeIf(alwaysTrue);
        if (result != false) {
            throw new AssertionError("Expected false return from removeIf");
        }
    }

    private static void testRemoveIfWithNullPredicate() {
        Collections c = new Collections() { };
        try {
            c.removeIf(null);
            throw new AssertionError("Expected NullPointerException for null predicate");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    // Since Collections.removeIf is an instance method in the snippet,
    // but in reality Collections is a utility class with static methods,
    // we simulate calling the method as instance method on an anonymous subclass.
    // This is to directly test the provided source code snippet behavior.

    // Note: The actual Collections class in JDK 11 does not have an instance method removeIf,
    // so this test is only valid for the provided snippet context.
}
