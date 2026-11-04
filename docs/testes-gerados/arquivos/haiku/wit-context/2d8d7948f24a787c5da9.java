/*
 * @test
 * @summary Verify that remove(Object, Object) on immutable maps throws UnsupportedOperationException and leaves the map unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;

public class ImmutableCollectionsWitupTest {

    interface Action {
        void run();
    }

    static void expectUoe(String label, Action action) {
        try {
            action.run();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new AssertionError("Expected UnsupportedOperationException: " + label);
    }

    public static void main(String[] args) {
        Map<String, Integer> empty = Map.of();
        expectUoe("empty map remove(k, v)", () -> empty.remove("a", 1));
        expectUoe("empty map remove(null, null)", () -> empty.remove(null, null));

        Map<String, Integer> one = Map.of("a", 1);
        expectUoe("single-entry map remove(present)", () -> one.remove("a", 1));
        expectUoe("single-entry map remove(absent)", () -> one.remove("b", 2));
        if (one.size() != 1 || one.get("a") != 1) {
            throw new AssertionError("Single-entry map was modified by remove(k, v)");
        }

        Map<String, Integer> two = Map.of("a", 1, "b", 2);
        expectUoe("multi-entry map remove(present)", () -> two.remove("b", 2));
        expectUoe("multi-entry map remove(wrong value)", () -> two.remove("a", 99));
        if (two.size() != 2 || two.get("a") != 1 || two.get("b") != 2) {
            throw new AssertionError("Multi-entry map was modified by remove(k, v)");
        }
    }
}
