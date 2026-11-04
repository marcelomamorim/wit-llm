/*
 * @test
 * @summary Verify removeIf behavior of the empty collection views returned by Collections
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testRemoveIfNullPredicateEmptyList();
        testRemoveIfNullPredicateEmptySet();
        testRemoveIfReturnsFalseEmptyList();
        testRemoveIfReturnsFalseEmptySet();
    }

    static void testRemoveIfNullPredicateEmptyList() {
        List<String> list = Collections.emptyList();
        try {
            list.removeIf(null);
            throw new RuntimeException("Expected NullPointerException for null filter on emptyList");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRemoveIfNullPredicateEmptySet() {
        Set<String> set = Collections.emptySet();
        try {
            set.removeIf(null);
            throw new RuntimeException("Expected NullPointerException for null filter on emptySet");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRemoveIfReturnsFalseEmptyList() {
        List<String> list = Collections.emptyList();
        boolean changed = list.removeIf(s -> true);
        if (changed) {
            throw new RuntimeException("removeIf on empty list should return false");
        }
        if (!list.isEmpty()) {
            throw new RuntimeException("empty list should remain empty");
        }
    }

    static void testRemoveIfReturnsFalseEmptySet() {
        Set<String> set = Collections.emptySet();
        boolean changed = set.removeIf(s -> false);
        if (changed) {
            throw new RuntimeException("removeIf on empty set should return false");
        }
        if (!set.isEmpty()) {
            throw new RuntimeException("empty set should remain empty");
        }
    }
}
