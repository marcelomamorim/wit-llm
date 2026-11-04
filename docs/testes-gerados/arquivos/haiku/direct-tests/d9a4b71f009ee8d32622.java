/*
 * @test
 * @summary Verifies that replace(K, V) on an unmodifiable map view throws UnsupportedOperationException and leaves the mapping unchanged
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

        expectUnsupported(unmodifiable, "a", 2);
        if (unmodifiable.get("a") != 1) {
            throw new AssertionError("mapping changed after failed replace");
        }
        if (base.get("a") != 1) {
            throw new AssertionError("backing map changed");
        }

        expectUnsupported(unmodifiable, "missing", 5);
        if (unmodifiable.containsKey("missing")) {
            throw new AssertionError("absent key was added");
        }
        if (unmodifiable.size() != 1) {
            throw new AssertionError("size changed: " + unmodifiable.size());
        }
    }

    private static void expectUnsupported(Map<String, Integer> map, String key, Integer value) {
        try {
            map.replace(key, value);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new AssertionError("expected UnsupportedOperationException for key " + key);
    }
}
