/*
 * @test
 * @summary Verify that remove(key, value) on an unmodifiable map throws UnsupportedOperationException and leaves the backing map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, Integer> base = new HashMap<>();
        base.put("a", 1);
        Map<String, Integer> unmodifiable = Collections.unmodifiableMap(base);

        expectUnsupported(unmodifiable, "a", 1);
        expectUnsupported(unmodifiable, "missing", 2);
        expectUnsupported(unmodifiable, null, null);

        if (base.size() != 1 || !base.containsKey("a") || base.get("a") != 1) {
            throw new RuntimeException("Backing map was modified");
        }
    }

    static void expectUnsupported(Map<String, Integer> map, Object key, Object value) {
        try {
            map.remove(key, value);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException for remove(" + key + ", " + value + ")");
    }
}
