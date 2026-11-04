/*
 * @test
 * @summary Verify that remove(Object, Object) on immutable maps throws UnsupportedOperationException and leaves the map unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        checkRemoveThrows(Map.of(), "a", "1");
        checkRemoveThrows(Map.of("a", "1"), "a", "1");
        checkRemoveThrows(Map.of("a", "1"), "b", "2");
        checkRemoveThrows(Map.of("a", "1"), "a", "2");
        checkRemoveThrows(Map.of("a", "1", "b", "2"), "b", "2");
    }

    static void checkRemoveThrows(Map<String, String> map, String key, String value) {
        int sizeBefore = map.size();
        try {
            map.remove(key, value);
        } catch (UnsupportedOperationException e) {
            if (map.size() != sizeBefore) {
                throw new RuntimeException("map size changed after failed remove");
            }
            if (!value.equals(map.get(key)) && map.containsKey(key)) {
                throw new RuntimeException("map content changed after failed remove");
            }
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException for remove(" + key + ", " + value + ")");
    }
}
