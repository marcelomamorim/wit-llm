/*
 * @test
 * @summary Verifies that putIfAbsent on an unmodifiable map view from Collections.unmodifiableMap throws UnsupportedOperationException and leaves the backing map unchanged
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

        expectUnsupported(unmodifiable, "b", 2);
        expectUnsupported(unmodifiable, "a", 5);

        if (unmodifiable.get("a").intValue() != 1) {
            throw new RuntimeException("existing value was modified");
        }
        if (unmodifiable.containsKey("b")) {
            throw new RuntimeException("new key was inserted");
        }
        if (base.size() != 1 || base.get("a").intValue() != 1) {
            throw new RuntimeException("backing map was modified");
        }
    }

    private static void expectUnsupported(Map<String, Integer> map, String key, Integer value) {
        try {
            map.putIfAbsent(key, value);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("putIfAbsent did not throw UnsupportedOperationException for key " + key);
    }
}
