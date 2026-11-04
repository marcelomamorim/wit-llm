/*
 * @test
 * @summary Verify that replace(K, V) on an unmodifiable map from Collections throws UnsupportedOperationException and leaves the map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {

    static void expectUnsupported(Map<String, Integer> map, String key, Integer value) {
        try {
            map.replace(key, value);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException for key " + key);
    }

    public static void main(String[] args) {
        Map<String, Integer> base = new HashMap<>();
        base.put("a", 1);
        Map<String, Integer> unmod = Collections.unmodifiableMap(base);

        expectUnsupported(unmod, "a", 2);
        expectUnsupported(unmod, "missing", 3);
        expectUnsupported(unmod, "a", null);

        if (unmod.get("a") != 1) {
            throw new RuntimeException("Unmodifiable map value changed");
        }
        if (unmod.containsKey("missing")) {
            throw new RuntimeException("Unmodifiable map gained a key");
        }
        if (base.get("a") != 1) {
            throw new RuntimeException("Backing map value changed");
        }
    }
}
