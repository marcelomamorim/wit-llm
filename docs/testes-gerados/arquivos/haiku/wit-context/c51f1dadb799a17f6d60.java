/*
 * @test
 * @summary Verifies that the replace(key, oldValue, newValue) view of an unmodifiable map always throws UnsupportedOperationException and leaves the backing map unchanged
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

        checkThrows(view, "a", 1, 2);
        checkThrows(view, "missing", null, 3);
        checkThrows(view, "a", 5, 6);

        if (base.size() != 1 || base.get("a") != 1 || base.containsKey("missing")) {
            throw new AssertionError("backing map was modified");
        }
        if (view.get("a") != 1) {
            throw new AssertionError("view value changed");
        }
    }

    private static void checkThrows(Map<String, Integer> m, String key, Integer oldValue, Integer newValue) {
        boolean thrown = false;
        try {
            m.replace(key, oldValue, newValue);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected UnsupportedOperationException for key " + key);
        }
    }
}
