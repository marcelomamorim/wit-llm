/*
 * @test
 * @summary Witup-derived checks for ConcurrentSkipListMap.put null handling and return value
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNullValueThrowsNPE();
        testNullKeyWithNonNullValueThrowsNPE();
        testPutReturnsPreviousValue();
    }

    static void testNullValueThrowsNPE() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("a", "1");
        try {
            map.put("b", null);
            throw new AssertionError("expected NullPointerException for null value");
        } catch (NullPointerException expected) {
        }
        if (map.containsKey("b")) {
            throw new AssertionError("null value must not be inserted");
        }
        if (!"1".equals(map.get("a"))) {
            throw new AssertionError("existing mapping must be preserved");
        }
    }

    static void testNullKeyWithNonNullValueThrowsNPE() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.put(null, "v");
            throw new AssertionError("expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }
        if (!map.isEmpty()) {
            throw new AssertionError("map must remain empty after failed put");
        }
    }

    static void testPutReturnsPreviousValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        if (map.put("k", "1") != null) {
            throw new AssertionError("first put must return null");
        }
        if (!"1".equals(map.put("k", "2"))) {
            throw new AssertionError("second put must return previous value");
        }
        if (!"2".equals(map.get("k"))) {
            throw new AssertionError("updated value must be visible");
        }
    }
}
