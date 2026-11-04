/*
 * @test
 * @summary Verify putIfAbsent on unmodifiable map views throws UnsupportedOperationException and leaves the map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        Map<String, Integer> backing = new HashMap<>();
        backing.put("a", 1);
        Map<String, Integer> view = Collections.unmodifiableMap(backing);

        expectUnsupported(view, "b", 2);
        expectUnsupported(view, "a", 9);

        if (view.get("a") == null || view.get("a").intValue() != 1) {
            throw new AssertionError("existing value was modified");
        }
        if (view.containsKey("b")) {
            throw new AssertionError("absent key was inserted");
        }
        if (view.size() != 1) {
            throw new AssertionError("unexpected size: " + view.size());
        }
    }

    private static void expectUnsupported(Map<String, Integer> map, String key, Integer value) {
        try {
            map.putIfAbsent(key, value);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new AssertionError("putIfAbsent did not throw UnsupportedOperationException for key: " + key);
    }
}
