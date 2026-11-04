/*
 * @test
 * @summary Verifies that removeIf on a singleton list throws UnsupportedOperationException and leaves the list unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testRemoveIfMatchingPredicateThrows();
        testRemoveIfNonMatchingPredicateThrows();
    }

    static void testRemoveIfMatchingPredicateThrows() {
        List<String> list = Collections.singletonList("a");
        boolean thrown = false;
        try {
            list.removeIf(s -> true);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from removeIf (matching predicate)");
        }
        if (list.size() != 1 || !"a".equals(list.get(0))) {
            throw new RuntimeException("Singleton list was modified after removeIf");
        }
    }

    static void testRemoveIfNonMatchingPredicateThrows() {
        List<String> list = Collections.singletonList("b");
        boolean thrown = false;
        try {
            list.removeIf(s -> false);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from removeIf (non-matching predicate)");
        }
        if (list.size() != 1 || !"b".equals(list.get(0))) {
            throw new RuntimeException("Singleton list was modified after removeIf");
        }
    }
}
