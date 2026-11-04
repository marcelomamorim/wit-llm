/*
 * @test
 * @summary Verify that removeIf on immutable List and Set instances always throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testListRemoveIfThrowsUOE();
        testListRemoveIfNonMatchingPredicateThrowsUOE();
        testEmptyListRemoveIfThrowsUOE();
        testSetRemoveIfThrowsUOE();
        testListUnchangedAfterRemoveIf();
    }

    static void testListRemoveIfThrowsUOE() {
        final List<Integer> list = List.of(1, 2, 3);
        final Predicate<Integer> p = x -> true;
        expectUOE(() -> list.removeIf(p), 'List.of removeIf with matching predicate');
    }

    static void testListRemoveIfNonMatchingPredicateThrowsUOE() {
        final List<Integer> list = List.of(1, 2, 3);
        final Predicate<Integer> p = x -> false;
        expectUOE(() -> list.removeIf(p), 'List.of removeIf with non-matching predicate');
    }

    static void testEmptyListRemoveIfThrowsUOE() {
        final List<Integer> list = List.of();
        final Predicate<Integer> p = x -> true;
        expectUOE(() -> list.removeIf(p), 'empty List.of removeIf');
    }

    static void testSetRemoveIfThrowsUOE() {
        final Set<Integer> set = Set.of(1, 2);
        final Predicate<Integer> p = x -> true;
        expectUOE(() -> set.removeIf(p), 'Set.of removeIf');
    }

    static void testListUnchangedAfterRemoveIf() {
        final List<Integer> list = List.of(1, 2, 3);
        final Predicate<Integer> p = x -> true;
        try {
            list.removeIf(p);
        } catch (UnsupportedOperationException e) {
            // expected
        }
        if (list.size() != 3 || !list.get(0).equals(1) || !list.get(1).equals(2) || !list.get(2).equals(3)) {
            throw new RuntimeException('List.of contents changed after removeIf');
        }
    }

    static void expectUOE(Runnable r, String description) {
        try {
            r.run();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException('Expected UnsupportedOperationException for ' + description);
    }
}
