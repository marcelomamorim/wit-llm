/*
 * @test
 * @summary Test java.util.Collections.EmptySet.forEach method for null action
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.Consumer;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Collections.EmptySet is package-private static final class inside Collections
        // but Collections.EMPTY_SET is public static final Set<?>
        // We use EMPTY_SET and cast to raw type to call forEach

        @SuppressWarnings("unchecked")
        java.util.Set<Object> emptySet = (java.util.Set<Object>) Collections.EMPTY_SET;

        // Test that forEach throws NullPointerException if action is null
        try {
            emptySet.forEach(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test that forEach with a non-null Consumer does not throw and does nothing
        emptySet.forEach(new Consumer<Object>() {
            public void accept(Object o) {
                throw new Error("Should not be called on empty set");
            }
        });
    }
}
