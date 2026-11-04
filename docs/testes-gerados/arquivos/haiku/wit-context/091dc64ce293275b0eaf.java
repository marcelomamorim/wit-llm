/*
 * @test
 * @summary Verify that clear() on immutable maps throws UnsupportedOperationException and leaves contents unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        checkClearThrows(Map.of());
        Map<String, Integer> m = Map.of("a", 1, "b", 2);
        checkClearThrows(m);
        if (m.size() != 2 || m.get("a") != 1 || m.get("b") != 2) {
            throw new RuntimeException("map contents changed after clear()");
        }
        checkClearThrows(Map.of("k", "v"));
    }

    static void checkClearThrows(Map<?, ?> map) {
        try {
            map.clear();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("clear() did not throw UnsupportedOperationException");
    }
}
