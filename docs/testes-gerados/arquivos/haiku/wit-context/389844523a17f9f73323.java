/*
 * @test
 * @summary Verify that immutable maps reject replace(K, V) with UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        checkReplaceThrows(Map.of(), "a", "b");
        checkReplaceThrows(Map.of("k", "v"), "k", "w");
        checkReplaceThrows(Map.of("k", "v"), "missing", "w");
        checkReplaceThrows(Map.ofEntries(Map.entry("k", "v"), Map.entry("x", "y")), "x", "z");
        checkReplaceThrows(Map.copyOf(Collections.singletonMap("k", "v")), "k", "u");
    }

    static void checkReplaceThrows(Map<String, String> map, String key, String value) {
        try {
            map.replace(key, value);
            throw new AssertionError("expected UnsupportedOperationException for " + map.getClass());
        } catch (UnsupportedOperationException expected) {
        }
    }
}
