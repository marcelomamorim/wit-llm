/*
 * @test
 * @summary Verifies that clear() on an unmodifiable map view throws UnsupportedOperationException and leaves the map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testClearOnEmptyUnmodifiableMapThrows();
        testClearOnNonEmptyUnmodifiableMapThrowsAndPreservesContent();
        testClearOnUnmodifiableMapOfEmptyMapThrowsRepeatedly();
    }

    private static void testClearOnEmptyUnmodifiableMapThrows() {
        Map<String, String> map = Collections.unmodifiableMap(new HashMap<String, String>());
        expectUnsupported(map);
        if (!map.isEmpty()) {
            throw new AssertionError("map should remain empty");
        }
    }

    private static void testClearOnNonEmptyUnmodifiableMapThrowsAndPreservesContent() {
        Map<String, String> backing = new HashMap<String, String>();
        backing.put("a", "1");
        backing.put("b", "2");
        Map<String, String> map = Collections.unmodifiableMap(backing);
        expectUnsupported(map);
        if (map.size() != 2) {
            throw new AssertionError("size changed after failed clear: " + map.size());
        }
        if (!"1".equals(map.get("a")) || !"2".equals(map.get("b"))) {
            throw new AssertionError("content changed after failed clear");
        }
    }

    private static void testClearOnUnmodifiableMapOfEmptyMapThrowsRepeatedly() {
        Map<String, String> map = Collections.unmodifiableMap(new HashMap<String, String>());
        expectUnsupported(map);
        expectUnsupported(map);
    }

    private static void expectUnsupported(Map<String, String> map) {
        try {
            map.clear();
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new AssertionError("expected UnsupportedOperationException from clear()");
    }
}
