/*
 * @test
 * @summary Test java.util.Collections.synchronizedMap(Map) for null argument and basic synchronization behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testNullArgument();
        testSynchronizedMapBasic();
    }

    private static void testNullArgument() {
        try {
            Collections.synchronizedMap(null);
            throw new RuntimeException("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testSynchronizedMapBasic() {
        Map<String, String> original = new HashMap<>();
        original.put("key1", "value1");
        Map<String, String> syncMap = Collections.synchronizedMap(original);

        // The synchronizedMap should reflect changes in the original map
        if (!syncMap.get("key1").equals("value1")) {
            throw new RuntimeException("synchronizedMap does not reflect original map contents");
        }

        // Modifications through synchronizedMap should affect original map
        syncMap.put("key2", "value2");
        if (!"value2".equals(original.get("key2"))) {
            throw new RuntimeException("Modification via synchronizedMap not reflected in original map");
        }

        // Test that keySet is not null and contains expected keys
        Set<String> keys = syncMap.keySet();
        if (keys == null || !keys.contains("key1") || !keys.contains("key2")) {
            throw new RuntimeException("keySet does not contain expected keys");
        }

        // Test iterator over keySet
        Iterator<String> it = keys.iterator();
        boolean foundKey1 = false;
        boolean foundKey2 = false;
        while (it.hasNext()) {
            String k = it.next();
            if ("key1".equals(k)) foundKey1 = true;
            if ("key2".equals(k)) foundKey2 = true;
        }
        if (!foundKey1 || !foundKey2) {
            throw new RuntimeException("Iterator over keySet missing expected keys");
        }
    }
}
