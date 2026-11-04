/*
 * @test
 * @summary Immutable map remove(Object) must throw UnsupportedOperationException and leave the map unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        checkRemoveThrows(Map.of(), "a");
        checkRemoveThrows(Map.of("a", 1), "a");
        checkRemoveThrows(Map.of("a", 1), "missing");
        checkRemoveThrows(Map.of("a", 1, "b", 2, "c", 3), "b");
        checkRemoveThrows(Map.of("a", 1, "b", 2, "c", 3), null);

        Map<String, Integer> m = Map.of("a", 1, "b", 2);
        try {
            m.remove("a");
            throw new RuntimeException("remove(\"a\") did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        if (m.size() != 2 || m.get("a") == null || m.get("a").intValue() != 1) {
            throw new RuntimeException("immutable map was modified by remove");
        }
    }

    static void checkRemoveThrows(Map<String, Integer> m, Object key) {
        try {
            m.remove(key);
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("remove(" + key + ") did not throw UnsupportedOperationException");
    }
}
