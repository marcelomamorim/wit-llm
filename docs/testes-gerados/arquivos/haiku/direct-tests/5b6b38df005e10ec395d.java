/*
 * @test
 * @summary Verify that replace(key, oldValue, newValue) on an unmodifiable map view throws UnsupportedOperationException and leaves the backing map unchanged
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

        check(um, "a", 1, 2);
        check(um, "a", 5, 6);
        check(um, "missing", null, 3);

        if (base.get("a").intValue() != 1) {
            throw new AssertionError("backing map value changed");
        }
        if (base.containsKey("missing")) {
            throw new AssertionError("backing map gained a key");
        }
    }

    static void check(Map<String, Integer> m, String key, Integer oldValue, Integer newValue) {
        try {
            m.replace(key, oldValue, newValue);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new AssertionError("expected UnsupportedOperationException for key " + key);
    }
}
