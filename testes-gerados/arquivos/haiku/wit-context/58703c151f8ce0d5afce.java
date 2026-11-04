/*
 * @test
 * @summary Verify that clear() on immutable List and Set instances throws UnsupportedOperationException and leaves contents unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        checkClearThrows(List.of(), "List.of()");

        List<Integer> list = List.of(1, 2, 3);
        checkClearThrows(list, "List.of(1,2,3)");
        if (!list.equals(List.of(1, 2, 3)) || list.size() != 3) {
            throw new RuntimeException("List contents changed after clear()");
        }

        Set<String> set = Set.of("a", "b");
        checkClearThrows(set, "Set.of(a,b)");
        if (set.size() != 2 || !set.contains("a") || !set.contains("b")) {
            throw new RuntimeException("Set contents changed after clear()");
        }
    }

    private static void checkClearThrows(Collection<?> c, String name) {
        int before = c.size();
        try {
            c.clear();
        } catch (UnsupportedOperationException e) {
            if (c.size() != before) {
                throw new RuntimeException(name + " size changed after failed clear()");
            }
            return;
        }
        throw new RuntimeException(name + ".clear() did not throw UnsupportedOperationException");
    }
}
