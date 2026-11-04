/*
 * @test
 * @summary Verify that immutable collections created by List.of and Set.of reject add with UnsupportedOperationException and keep their contents unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;
import java.util.Set;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3);
        expectUoe(() -> list.add(4));
        if (list.size() != 3 || list.get(0) != 1 || list.get(1) != 2 || list.get(2) != 3) {
            throw new AssertionError("list changed after rejected add: " + list);
        }

        List<Integer> empty = List.of();
        expectUoe(() -> empty.add(1));
        if (!empty.isEmpty()) {
            throw new AssertionError("empty list changed after rejected add");
        }

        Set<String> set = Set.of("a");
        expectUoe(() -> set.add("b"));
        if (set.size() != 1 || !set.contains("a") || set.contains("b")) {
            throw new AssertionError("set changed after rejected add: " + set);
        }
    }

    static void expectUoe(Runnable r) {
        try {
            r.run();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new AssertionError("expected UnsupportedOperationException");
    }
}
