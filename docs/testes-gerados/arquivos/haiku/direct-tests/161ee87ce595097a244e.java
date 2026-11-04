/*
 * @test
 * @summary Verifies that remove on an unmodifiable map view throws UnsupportedOperationException and leaves the map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, Integer> base = new HashMap<>();
        base.put("a", 1);
        Map<String, Integer> um = Collections.unmodifiableMap(base);

        expectUnsupported(um, "a");
        expectUnsupported(um, "missing");
        expectUnsupported(um, null);

        if (um.size() != 1) {
            throw new RuntimeException("size changed: " + um.size());
        }
        if (um.get("a").intValue() != 1) {
            throw new RuntimeException("value changed");
        }
        if (um.containsKey("missing")) {
            throw new RuntimeException("unexpected key present");
        }
    }

    static void expectUnsupported(Map<String, Integer> m, Object key) {
        try {
            m.remove(key);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("no UnsupportedOperationException for key " + key);
    }
}
