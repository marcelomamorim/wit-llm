/*
 * @test
 * @summary Verifies that the unmodifiable entry set returned by Collections.unmodifiableMap rejects add(Map.Entry) with UnsupportedOperationException and leaves the backing map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        Map<String, Integer> base = new HashMap<>();
        base.put("a", 1);
        Map<String, Integer> um = Collections.unmodifiableMap(base);
        Set<Map.Entry<String, Integer>> es = um.entrySet();

        expectUnsupported(es, Map.entry("b", 2));
        expectUnsupported(es, Map.entry("a", 1));
        expectUnsupported(es, null);

        if (base.size() != 1 || !base.containsKey("a") || base.containsKey("b")) {
            throw new RuntimeException("backing map was modified: " + base);
        }
        if (um.size() != 1) {
            throw new RuntimeException("unexpected size: " + um.size());
        }
    }

    private static void expectUnsupported(Set<Map.Entry<String, Integer>> set,
                                          Map.Entry<String, Integer> entry) {
        boolean thrown = false;
        try {
            set.add(entry);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected UnsupportedOperationException for entry: " + entry);
        }
    }
}
