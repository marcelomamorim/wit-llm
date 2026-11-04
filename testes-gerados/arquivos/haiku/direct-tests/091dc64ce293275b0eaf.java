/*
 * @test
 * @summary ImmutableCollections clear() must throw UnsupportedOperationException and leave the collection unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3);
        expectUoe(list, "List.of(1,2,3)");
        if (list.size() != 3 || !list.contains(1) || !list.contains(3)) {
            throw new RuntimeException("List content changed after clear()");
        }

        expectUoe(List.of(), "List.of()");
        expectUoe(Set.of("a", "b"), "Set.of(a,b)");
        expectUoe(Set.of("x"), "Set.of(x)");

        Set<String> set = Set.of("a", "b");
        expectUoe(set, "Set.of(a,b) reuse");
        if (set.size() != 2 || !set.contains("a") || !set.contains("b")) {
            throw new RuntimeException("Set content changed after clear()");
        }

        Map<String, Integer> map = Map.of("k", 1);
        expectMapUoe(map, "Map.of(k,1)");
        if (map.size() != 1 || map.get("k") == null || map.get("k").intValue() != 1) {
            throw new RuntimeException("Map content changed after clear()");
        }
    }

    static void expectUoe(Collection<?> c, String name) {
        boolean thrown = false;
        try {
            c.clear();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException(name + ".clear() did not throw UnsupportedOperationException");
        }
    }

    static void expectMapUoe(Map<?, ?> m, String name) {
        boolean thrown = false;
        try {
            m.clear();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException(name + ".clear() did not throw UnsupportedOperationException");
        }
    }
}
