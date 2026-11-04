/*
 * @test
 * @summary Verifies that replace(key, oldValue, newValue) on immutable maps always throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        checkThrowsUoe(Map.of(), "a", 1, 2);
        checkThrowsUoe(Map.of("a", 1), "a", 1, 2);
        checkThrowsUoe(Map.of("a", 1), "a", 3, 2);
        checkThrowsUoe(Map.of("a", 1, "b", 2), "b", 2, 5);
        checkThrowsUoe(Map.of("a", 1, "b", 2), null, 1, 2);
    }

    static void checkThrowsUoe(Map<String, Integer> map, String key, Integer oldValue, Integer newValue) {
        try {
            map.replace(key, oldValue, newValue);
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException from replace on immutable map");
    }
}
