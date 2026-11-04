/*
 * @test
 * @summary Verifies that remove(key, value) on an unmodifiable map from Collections throws UnsupportedOperationException and leaves the backing map unchanged
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

        expectUnsupported(() -> um.remove("a", 1));
        if (!Integer.valueOf(1).equals(base.get("a")) || base.size() != 1) {
            throw new RuntimeException("backing map was modified by remove(key, value)");
        }
        if (!Integer.valueOf(1).equals(um.get("a"))) {
            throw new RuntimeException("unmodifiable view changed after remove(key, value)");
        }

        expectUnsupported(() -> um.remove("absent", 2));
        expectUnsupported(() -> um.remove(null, null));

        if (base.size() != 1) {
            throw new RuntimeException("unexpected size change: " + base.size());
        }
    }

    private static void expectUnsupported(Runnable r) {
        try {
            r.run();
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("UnsupportedOperationException was not thrown");
    }
}
