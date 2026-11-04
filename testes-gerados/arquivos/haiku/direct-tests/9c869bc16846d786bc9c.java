/*
 * @test
 * @summary Verify that replace(K,V,V) on immutable maps throws UnsupportedOperationException and leaves the mapping unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, Integer> empty = Map.of();
        Map<String, Integer> one = Map.of("a", 1);
        Map<String, Integer> two = Map.of("a", 1, "b", 2);

        expectUoe(() -> empty.replace("a", 1, 2), "empty map, absent key");
        expectUoe(() -> one.replace("a", 1, 2), "single entry, matching old value");
        expectUoe(() -> one.replace("a", 99, 2), "single entry, non-matching old value");
        expectUoe(() -> one.replace("z", 1, 2), "single entry, absent key");
        expectUoe(() -> two.replace("a", 1, 3), "multi entry, matching old value");
        expectUoe(() -> two.replace("b", 5, 3), "multi entry, non-matching old value");

        check(one.get("a") == 1, "single-entry map was modified");
        check(two.get("a") == 1, "multi-entry map key a was modified");
        check(two.get("b") == 2, "multi-entry map key b was modified");
        check(one.size() == 1, "single-entry map size changed");
        check(two.size() == 2, "multi-entry map size changed");
    }

    static void expectUoe(Runnable r, String msg) {
        try {
            r.run();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("UnsupportedOperationException not thrown: " + msg);
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }
}
