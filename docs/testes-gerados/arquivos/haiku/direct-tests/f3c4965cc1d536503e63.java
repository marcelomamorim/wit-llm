/*
 * @test
 * @summary Verify that retainAll on immutable List.of and Set.of collections throws UnsupportedOperationException and leaves contents unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ImmutableCollectionsWitupTest {
    interface Action { void run(); }

    static int failures = 0;

    static void expectUoe(String name, Action a) {
        try {
            a.run();
            System.err.println("FAIL: no exception: " + name);
            failures++;
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

    public static void main(String[] args) {
        List<String> list = List.of("a", "b");
        expectUoe("list retainAll non-empty", () -> list.retainAll(Arrays.asList("a")));
        expectUoe("list retainAll empty collection", () -> list.retainAll(Collections.emptyList()));
        expectUoe("list retainAll null", () -> list.retainAll(null));

        List<String> empty = List.of();
        expectUoe("empty list retainAll", () -> empty.retainAll(new ArrayList<String>()));

        Set<String> set = Set.of("x", "y");
        expectUoe("set retainAll", () -> set.retainAll(Set.of("x")));

        Set<String> single = Set.of("z");
        expectUoe("single set retainAll", () -> single.retainAll(Collections.emptySet()));

        if (list.size() != 2 || !list.contains("a") || !list.contains("b")) {
            System.err.println("FAIL: list contents changed");
            failures++;
        }
        if (set.size() != 2 || !set.contains("x") || !set.contains("y")) {
            System.err.println("FAIL: set contents changed");
            failures++;
        }

        if (failures > 0) {
            throw new RuntimeException(failures + " failure(s)");
        }
    }
}
