/*
 * @test
 * @summary Verify that remove(key, value) on unmodifiable maps throws UnsupportedOperationException and leaves the backing map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, Integer> base = new HashMap<>();
        base.put("a", 1);
        Map<String, Integer> un = Collections.unmodifiableMap(base);

        boolean thrown = false;
        try {
            un.remove("a", 1);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected UnsupportedOperationException for matching entry");
        }

        thrown = false;
        try {
            un.remove("missing", 2);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected UnsupportedOperationException for absent entry");
        }

        thrown = false;
        try {
            un.remove(null, null);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected UnsupportedOperationException for null key and value");
        }

        if (base.size() != 1 || !Integer.valueOf(1).equals(base.get("a"))) {
            throw new AssertionError("backing map must remain unchanged");
        }
    }
}
