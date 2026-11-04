/*
 * @test
 * @summary Verifies that remove(key, value) on an unmodifiable map view from Collections throws UnsupportedOperationException and leaves the backing map unchanged
 * @run main CollectionsWitupTest
 */
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        Map<String, String> base = new HashMap<>();
        base.put("k", "v");
        base.put("other", "x");
        Map<String, String> unmodifiable = Collections.unmodifiableMap(base);

        expectUnsupported(unmodifiable, "k", "v");
        expectUnsupported(unmodifiable, "k", "wrong");
        expectUnsupported(unmodifiable, "missing", "v");
        expectUnsupported(unmodifiable, null, null);

        if (!"v".equals(base.get("k"))) {
            throw new AssertionError("backing map entry k was modified");
        }
        if (!"x".equals(base.get("other"))) {
            throw new AssertionError("backing map entry other was modified");
        }
        if (base.size() != 2) {
            throw new AssertionError("backing map size changed: " + base.size());
        }
    }

    private static void expectUnsupported(Map<String, String> map, Object key, Object value) {
        try {
            map.remove(key, value);
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new AssertionError("expected UnsupportedOperationException for remove(" + key + ", " + value + ")");
    }
}
