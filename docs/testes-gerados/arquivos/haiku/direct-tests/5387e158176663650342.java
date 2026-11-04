/*
 * @test
 * @summary Verifies that put on a Collections unmodifiable map view throws UnsupportedOperationException and leaves the backing map unchanged
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

        checkPutThrowsUOE(view, "b", 2);
        checkPutThrowsUOE(view, "a", 99);
        checkPutThrowsUOE(view, null, 3);

        if (base.size() != 1) {
            throw new RuntimeException("backing map size changed: " + base.size());
        }
        if (base.get("a") == null || base.get("a").intValue() != 1) {
            throw new RuntimeException("backing map value changed: " + base.get("a"));
        }
        if (base.containsKey("b")) {
            throw new RuntimeException("unexpected key added to backing map");
        }
        if (view.size() != 1 || view.get("a").intValue() != 1) {
            throw new RuntimeException("view does not reflect unchanged state");
        }
    }

    private static void checkPutThrowsUOE(Map<String, Integer> map, String key, Integer value) {
        try {
            map.put(key, value);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("put did not throw UnsupportedOperationException for key: " + key);
    }
}
