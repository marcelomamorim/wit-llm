/*
 * @test
 * @summary Verify that add(E) on immutable collections throws UnsupportedOperationException and leaves contents unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        checkAddThrows(List.of(), "List.of()");
        checkAddThrows(List.of(1), "List.of(1)");
        checkAddThrows(List.of(1, 2, 3), "List.of(1,2,3)");
        checkAddThrows(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), "List.of(11 elements)");
        checkAddThrows(Set.of(), "Set.of()");
        checkAddThrows(Set.of(1), "Set.of(1)");
        checkAddThrows(Set.of(1, 2, 3), "Set.of(1,2,3)");
    }

    static void checkAddThrows(Collection<Integer> c, String name) {
        int before = c.size();
        try {
            c.add(42);
            throw new AssertionError(name + ": add(42) did not throw");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        if (c.size() != before) {
            throw new AssertionError(name + ": size changed after failed add");
        }

        try {
            c.add(null);
            throw new AssertionError(name + ": add(null) did not throw");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        if (c.size() != before) {
            throw new AssertionError(name + ": size changed after failed add(null)");
        }
    }
}
