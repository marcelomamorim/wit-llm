/*
 * @test
 * @summary Verify that addAll on immutable collections from List.of and Set.of always throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testListAddAllWithNonEmptyArgument();
        testListAddAllWithEmptyArgument();
        testListOfEmptyAddAll();
        testSetAddAll();
        testUnmodifiableListRemainsUnchangedAfterFailedAddAll();
    }

    static void expectUOE(Runnable action, String name) {
        try {
            action.run();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new AssertionError(name + ": expected UnsupportedOperationException");
    }

    static void testListAddAllWithNonEmptyArgument() {
        final List<Integer> list = List.of(1, 2);
        expectUOE(() -> list.addAll(Arrays.asList(3, 4)), "List.of addAll non-empty");
    }

    static void testListAddAllWithEmptyArgument() {
        final List<Integer> list = List.of(1, 2, 3);
        expectUOE(() -> list.addAll(Collections.emptyList()), "List.of addAll empty argument");
    }

    static void testListOfEmptyAddAll() {
        final List<String> list = List.of();
        expectUOE(() -> list.addAll(new ArrayList<String>()), "List.of() addAll");
    }

    static void testSetAddAll() {
        final Set<String> set = Set.of("a", "b");
        expectUOE(() -> set.addAll(Arrays.asList("c")), "Set.of addAll");
    }

    static void testUnmodifiableListRemainsUnchangedAfterFailedAddAll() {
        final List<Integer> list = List.of(1, 2);
        expectUOE(() -> list.addAll(Arrays.asList(5)), "List.of addAll state check");
        if (list.size() != 2 || !list.get(0).equals(1) || !list.get(1).equals(2)) {
            throw new AssertionError("List changed after failed addAll");
        }
    }
}
