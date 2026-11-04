/*
 * @test
 * @summary Test java.util.Collections removeIf method behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.Predicate;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveIfNullPredicate();
        testRemoveIfReturnsFalse();
    }

    private static void testRemoveIfNullPredicate() {
        try {
            Collections.emptySet().removeIf(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testRemoveIfReturnsFalse() {
        boolean result = Collections.emptySet().removeIf(new Predicate<Object>() {
            public boolean test(Object o) {
                return true;
            }
        });
        if (result) {
            throw new RuntimeException("removeIf on emptySet should return false");
        }
    }
}
