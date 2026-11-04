/*
 * @test
 * @summary Verify Collections.emptySet().removeIf null-check and false-return behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Set;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testNullFilterThrowsNPE();
        testNonMatchingPredicateReturnsFalse();
        testMatchingPredicateReturnsFalseAndKeepsEmpty();
        testEmptySetRemainsEmpty();
    }

    static void testNullFilterThrowsNPE() {
        Set<Integer> set = Collections.emptySet();
        boolean thrown = false;
        try {
            set.removeIf(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null filter");
        }
    }

    static void testNonMatchingPredicateReturnsFalse() {
        Set<Integer> set = Collections.emptySet();
        boolean result = set.removeIf(x -> false);
        if (result) {
            throw new RuntimeException("removeIf on empty set must return false");
        }
    }

    static void testMatchingPredicateReturnsFalseAndKeepsEmpty() {
        Set<Integer> set = Collections.emptySet();
        boolean result = set.removeIf(x -> true);
        if (result) {
            throw new RuntimeException("removeIf with always-true predicate on empty set must return false");
        }
        if (!set.isEmpty()) {
            throw new RuntimeException("empty set must remain empty after removeIf");
        }
    }

    static void testEmptySetRemainsEmpty() {
        Set<String> set = Collections.<String>emptySet();
        set.removeIf(s -> s == null);
        if (set.size() != 0) {
            throw new RuntimeException("size must remain 0 after removeIf");
        }
    }
}
