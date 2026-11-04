/*
 * @test
 * @summary Test java.util.Collections removeIf method for null filter and normal behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveIfNullFilter();
        testRemoveIfNormal();
    }

    private static void testRemoveIfNullFilter() {
        Set<String> set = new HashSet<>();
        set.add("a");
        try {
            // Collections.removeIf calls s.removeIf(filter), which throws NullPointerException if filter is null
            Collections.removeIf(set, null);
            throw new RuntimeException("Expected NullPointerException for null filter");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRemoveIfNormal() {
        Set<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        // Remove elements equal to "a"
        boolean removed = Collections.removeIf(set, new Predicate<String>() {
            public boolean test(String s) {
                return "a".equals(s);
            }
        });
        if (!removed) {
            throw new RuntimeException("Expected element to be removed");
        }
        if (set.contains("a")) {
            throw new RuntimeException("Element 'a' should have been removed");
        }
        if (!set.contains("b")) {
            throw new RuntimeException("Element 'b' should remain");
        }
    }
}
