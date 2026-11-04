/*
 * @test
 * @summary Verify Collections.emptyMap().remove(key, value) throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, String> m = Collections.emptyMap();
        expectUnsupported(m, "k", "v");
        expectUnsupported(m, null, null);
        expectUnsupported(m, "k", null);
        if (!m.isEmpty()) {
            throw new RuntimeException("emptyMap changed after remove");
        }
    }

    static void expectUnsupported(Map<String, String> m, Object key, Object value) {
        try {
            m.remove(key, value);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException for key=" + key + ", value=" + value);
    }
}
