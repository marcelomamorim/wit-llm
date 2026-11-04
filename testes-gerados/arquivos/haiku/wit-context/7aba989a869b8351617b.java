/*
 * @test
 * @summary Tests Collections.synchronizedMap null-argument contract and wrapper view behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testNullMapThrowsNPE();
        testReturnsNonNullWrapper();
        testWrapperReflectsBackingMap();
    }

    static void testNullMapThrowsNPE() {
        boolean thrown = false;
        try {
            Collections.synchronizedMap((Map<String, Integer>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null map");
        }
    }

    static void testReturnsNonNullWrapper() {
        Map<String, Integer> backing = new HashMap<>();
        Map<String, Integer> sync = Collections.synchronizedMap(backing);
        if (sync == null) {
            throw new RuntimeException("synchronizedMap returned null");
        }
        if (sync.size() != 0) {
            throw new RuntimeException("Expected empty wrapper, size=" + sync.size());
        }
    }

    static void testWrapperReflectsBackingMap() {
        Map<String, Integer> backing = new HashMap<>();
        Map<String, Integer> sync = Collections.synchronizedMap(backing);

        sync.put("a", 1);
        if (backing.get("a") == null || backing.get("a") != 1) {
            throw new RuntimeException("Put via wrapper not visible in backing map");
        }

        backing.put("b", 2);
        if (sync.get("b") == null || sync.get("b") != 2) {
            throw new RuntimeException("Put in backing map not visible via wrapper");
        }

        if (sync.size() != 2) {
            throw new RuntimeException("Expected size 2, got " + sync.size());
        }
    }
}
