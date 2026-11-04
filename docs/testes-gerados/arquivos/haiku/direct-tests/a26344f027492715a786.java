/*
 * @test
 * @summary Collections.unmodifiableMap(...).replace(key, oldValue, newValue) must throw UnsupportedOperationException and leave the backing map unchanged
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

        expectUnsupported(unmodifiable, "a", 1, 2);
        expectUnsupported(unmodifiable, "a", 99, 2);
        expectUnsupported(unmodifiable, "missing", null, 5);

        if (!Integer.valueOf(1).equals(unmodifiable.get("a"))) {
            throw new RuntimeException("unmodifiable map value changed");
        }
        if (unmodifiable.containsKey("missing")) {
            throw new RuntimeException("unmodifiable map gained a key");
        }
        if (base.size() != 1 || !Integer.valueOf(1).equals(base.get("a"))) {
            throw new RuntimeException("backing map was modified");
        }
    }

    private static void expectUnsupported(Map<String, Integer> map,
                                          String key, Integer oldValue, Integer newValue) {
        try {
            map.replace(key, oldValue, newValue);
            throw new RuntimeException("expected UnsupportedOperationException for key " + key);
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
