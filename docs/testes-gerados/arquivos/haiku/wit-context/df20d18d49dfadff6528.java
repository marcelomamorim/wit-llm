/*
 * @test
 * @summary Verify that computeIfAbsent on immutable maps from Map.of throws UnsupportedOperationException and leaves the map unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        check(Map.of(), "k");
        check(Map.of("a", 1), "b");
        check(Map.of("a", 1), "a");
        check(Map.of("a", 1, "b", 2, "c", 3), "d");
        check(Map.of("a", 1, "b", 2, "c", 3), "b");
    }

    static void check(Map<String, Integer> m, String key) {
        int before = m.size();
        boolean thrown = false;
        try {
            m.computeIfAbsent(key, k -> 42);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException for key " + key);
        }
        if (m.size() != before) {
            throw new RuntimeException("Map size changed after failed computeIfAbsent");
        }
    }
}
