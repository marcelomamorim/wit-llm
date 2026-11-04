/*
 * @test
 * @summary Verify that the unmodifiable map wrapper from Collections rejects computeIfAbsent with UnsupportedOperationException and leaves the map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testComputeIfAbsentThrowsUnsupported();
        testMappingFunctionNotInvoked();
        testMapRemainsEmpty();
        testExistingEntryNotModified();
    }

    static void testComputeIfAbsentThrowsUnsupported() {
        Map<String, Integer> base = new HashMap<>();
        Map<String, Integer> map = Collections.unmodifiableMap(base);
        boolean thrown = false;
        try {
            map.computeIfAbsent("k", k -> 1);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException");
        }
    }

    static void testMappingFunctionNotInvoked() {
        Map<String, Integer> map = Collections.unmodifiableMap(new HashMap<String, Integer>());
        boolean[] called = {false};
        try {
            map.computeIfAbsent("k", k -> {
                called[0] = true;
                return 1;
            });
        } catch (UnsupportedOperationException e) {
            // expected
        }
        if (called[0]) {
            throw new RuntimeException("Mapping function must not be invoked");
        }
    }

    static void testMapRemainsEmpty() {
        Map<String, Integer> base = new HashMap<>();
        Map<String, Integer> map = Collections.unmodifiableMap(base);
        try {
            map.computeIfAbsent("k", k -> 1);
        } catch (UnsupportedOperationException e) {
            // expected
        }
        if (!map.isEmpty() || map.containsKey("k")) {
            throw new RuntimeException("Map should remain empty");
        }
    }

    static void testExistingEntryNotModified() {
        Map<String, Integer> base = new HashMap<>();
        base.put("k", 10);
        Map<String, Integer> map = Collections.unmodifiableMap(base);
        try {
            map.computeIfAbsent("k", key -> 20);
        } catch (UnsupportedOperationException e) {
            // expected
        }
        if (map.get("k") != 10) {
            throw new RuntimeException("Existing value must not change");
        }
    }
}
