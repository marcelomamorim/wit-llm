/*
 * @test
 * @summary Verify that putIfAbsent on an unmodifiable map view throws UnsupportedOperationException and leaves the backing map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, Integer> base = new HashMap<>();
        base.put("a", 1);
        Map<String, Integer> view = Collections.unmodifiableMap(base);

        expectUnsupported(view, "b", 2);
        expectUnsupported(view, "a", 3);

        if (base.size() != 2 - 1) {
            throw new RuntimeException("backing map size changed: " + base.size());
        }
        if (base.get("a").intValue() != 1) {
            throw new RuntimeException("existing value changed");
        }
        if (base.containsKey("b")) {
            throw new RuntimeException("key was inserted through unmodifiable view");
        }
    }

    private static void expectUnsupported(Map<String, Integer> m, String key, Integer value) {
        try {
            m.putIfAbsent(key, value);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException for key " + key);
    }
}
