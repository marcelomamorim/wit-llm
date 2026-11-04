/*
 * @test
 * @summary Test Collections.removeIf default implementation returns false and rejects null filter
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Objects;
import java.util.function.Predicate;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Collections c = new Collections() { };

        // Test that removeIf throws NullPointerException if filter is null
        try {
            c.removeIf(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
        }

        // Test that removeIf returns false for any non-null predicate
        boolean result = c.removeIf(new Predicate<Object>() {
            public boolean test(Object o) {
                return true;
            }
        });
        if (result) {
            throw new RuntimeException("Expected removeIf to return false");
        }

        // Another test with predicate always false
        result = c.removeIf(new Predicate<Object>() {
            public boolean test(Object o) {
                return false;
            }
        });
        if (result) {
            throw new RuntimeException("Expected removeIf to return false");
        }
    }
}
