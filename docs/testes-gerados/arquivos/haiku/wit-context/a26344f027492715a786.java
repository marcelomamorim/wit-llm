/*
 * @test
 * @summary Verify that replace(K,V,V) on a Collections.singletonMap throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, Integer> m = Collections.singletonMap("a", 1);
        expectUnsupported(m, "a", 1, 2);
        expectUnsupported(m, "a", 2, 3);
        expectUnsupported(m, "b", 1, 2);
        if (!"a".equals(m.keySet().iterator().next()) || m.get("a") != 1 || m.size() != 1) {
            throw new RuntimeException("singleton map state changed");
        }
    }

    static void expectUnsupported(Map<String, Integer> m, String key, Integer oldValue, Integer newValue) {
        boolean thrown = false;
        try {
            m.replace(key, oldValue, newValue);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected UnsupportedOperationException for key " + key);
        }
    }
}
