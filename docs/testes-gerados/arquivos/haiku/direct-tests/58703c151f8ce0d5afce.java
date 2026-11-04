/*
 * @test
 * @summary Verifies that clear() on immutable collections throws UnsupportedOperationException and leaves contents unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        expectUoe(List.of(), "List.of().clear()");
        expectUoe(List.of(1, 2, 3), "List.of(1,2,3).clear()");
        expectUoe(Set.of(), "Set.of().clear()");
        expectUoe(Set.of("a"), "Set.of(\"a\").clear()");

        List<Integer> list = List.of(1, 2, 3);
        try {
            list.clear();
            throw new RuntimeException("clear() did not throw");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        if (list.size() != 3 || !list.get(0).equals(1) || !list.get(2).equals(3)) {
            throw new RuntimeException("List contents changed after failed clear()");
        }

        Set<String> set = Set.of("a");
        try {
            set.clear();
            throw new RuntimeException("Set clear() did not throw");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        if (set.size() != 1 || !set.contains("a")) {
            throw new RuntimeException("Set contents changed after failed clear()");
        }
    }

    static void expectUoe(Collection<?> c, String name) {
        try {
            c.clear();
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException(name + " did not throw UnsupportedOperationException");
    }
}
