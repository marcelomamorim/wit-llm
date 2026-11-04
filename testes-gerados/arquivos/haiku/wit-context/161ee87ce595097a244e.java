/*
 * @test
 * @summary Verify that remove on an unmodifiable map view throws UnsupportedOperationException and leaves the backing map unchanged
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

        expectUnsupported(view, "a");
        expectUnsupported(view, "missing");
        expectUnsupported(view, null);

        if (view.size() != 1) {
            throw new RuntimeException("size changed: " + view.size());
        }
        if (!Integer.valueOf(1).equals(view.get("a"))) {
            throw new RuntimeException("entry changed: " + view.get("a"));
        }
        if (base.size() != 1) {
            throw new RuntimeException("backing map changed: " + base.size());
        }
    }

    private static void expectUnsupported(Map<String, Integer> m, Object key) {
        try {
            m.remove(key);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException for key " + key);
    }
}
