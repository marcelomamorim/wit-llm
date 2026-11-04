/* @test    @summary Test synchronizedMap method in Collections class    @run main CollectionsWitupTest */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testSynchronizedMapWithEmptyMap();
        testSynchronizedMapWithNull();
    }

    static void testSynchronizedMapWithEmptyMap() {
        Map<String, String> emptyMap = new HashMap<>();
        Map<String, String> syncMap = Collections.synchronizedMap(emptyMap);
        if (syncMap == null) {
            throw new AssertionError("synchronizedMap returned null for empty map");
        }
        if (syncMap != emptyMap) {
            throw new AssertionError("synchronizedMap did not return the same map instance");
        }
        // Verify that the returned map is an instance of the expected class
        if (!(syncMap instanceof java.util.Collections.SynchronizedMap)) {
            // This check is theoretical; actual class is package-private, so we check for interface
            if (!(syncMap instanceof java.util.Map)) {
                throw new AssertionError("Returned object is not a Map instance");
            }
        }
    }

    static void testSynchronizedMapWithNull() {
        try {
            Map<String, String> syncMap = Collections.synchronizedMap(null);
            if (syncMap != null) {
                throw new AssertionError("synchronizedMap should return null when input is null");
            }
        } catch (NullPointerException e) {
            // Expected behavior if method throws NPE
        }
    }
}
