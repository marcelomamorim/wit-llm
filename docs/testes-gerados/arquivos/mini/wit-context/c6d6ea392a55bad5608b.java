/*
 * @test
 * @summary Test java.util.Collections removeIf method behavior and null filter handling
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.Predicate;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveIfWithNullFilter();
        testRemoveIfReturnsFalse();
    }

    private static void testRemoveIfWithNullFilter() {
        try {
            Collections.emptyList().removeIf(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testRemoveIfReturnsFalse() {
        boolean result = Collections.emptyList().removeIf(new Predicate<Object>() {
            public boolean test(Object o) {
                return true;
            }
        });
        if (result) {
            throw new Error("Expected removeIf to return false on empty list");
        }
    }
}
