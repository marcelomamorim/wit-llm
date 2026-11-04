/*
 * @test
 * @summary Verify that remove(Object) on immutable maps created by Map.of throws UnsupportedOperationException and leaves the map unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        checkRemoveThrowsUOE(Map.of("a", 1), "a");
        checkRemoveThrowsUOE(Map.of("a", 1), "missing");
        checkRemoveThrowsUOE(Map.of("a", 1, "b", 2), "a");
        checkRemoveThrowsUOE(Map.of(), "missing");
        checkRemoveThrowsUOE(Map.of("a", 1), null);
    }

    static void checkRemoveThrowsUOE(Map<String, Integer> map, Object key) {
        int sizeBefore = map.size();
        try {
            map.remove(key);
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        if (map.size() != sizeBefore) {
            throw new RuntimeException("Map size changed after failed remove");
        }
    }
}
