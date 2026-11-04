/*
 * @test
 * @summary Verify that putIfAbsent on immutable maps created by Map.of throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        checkUOE(Map.of(), "k", "v");
        checkUOE(Map.of("a", 1), "b", 2);
        checkUOE(Map.of("a", 1), "a", 2);
        checkUOE(Map.of("a", 1, "b", 2), "c", 3);
        checkUOE(Map.of("a", 1), null, null);
        checkUOE(Map.of(), null, 1);

        Map<String, Integer> m = Map.of("a", 1);
        checkUOE(m, "a", 5);
        if (m.get("a") != 1 || m.size() != 1) {
            throw new RuntimeException("immutable map was modified");
        }
    }

    private static <K, V> void checkUOE(Map<K, V> map, K key, V value) {
        try {
            map.putIfAbsent(key, value);
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("putIfAbsent did not throw UnsupportedOperationException");
    }
}
