/*
 * @test
 * @summary Verify that the unmodifiable Map view returned by Collections.unmodifiableMap rejects put with UnsupportedOperationException and leaves the backing map unchanged
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

        expectUnsupported(unmodifiable, "k", 2);
        expectUnsupported(unmodifiable, "a", 3);
        expectUnsupported(unmodifiable, null, null);

        if (base.size() != 1 || !Integer.valueOf(1).equals(base.get("a")) || base.containsKey("k")) {
            throw new RuntimeException("backing map was modified through unmodifiable view");
        }
        if (!Integer.valueOf(1).equals(unmodifiable.get("a")) || unmodifiable.size() != 1) {
            throw new RuntimeException("unmodifiable view state changed after failed put");
        }
    }

    private static void expectUnsupported(Map<String, Integer> map, String key, Integer value) {
        try {
            map.put(key, value);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException for put(" + key + ", " + value + ")");
    }
}
