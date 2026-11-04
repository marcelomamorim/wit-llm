/*
 * @test
 * @summary Tests putIfAbsent of ConcurrentSkipListMap for absent/present keys and null key or value
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testPutIfAbsentAbsentKey();
        testPutIfAbsentPresentKeyDoesNotOverwrite();
        testPutIfAbsentNullValue();
        testPutIfAbsentNullKey();
    }

    static void testPutIfAbsentAbsentKey() {
        ConcurrentSkipListMap<String, String> m = new ConcurrentSkipListMap<>();
        if (m.putIfAbsent("a", "1") != null) {
            throw new RuntimeException("expected null when key is absent");
        }
        if (!"1".equals(m.get("a"))) {
            throw new RuntimeException("value not stored for absent key");
        }
    }

    static void testPutIfAbsentPresentKeyDoesNotOverwrite() {
        ConcurrentSkipListMap<String, String> m = new ConcurrentSkipListMap<>();
        m.put("a", "1");
        if (!"1".equals(m.putIfAbsent("a", "2"))) {
            throw new RuntimeException("expected existing value to be returned");
        }
        if (!"1".equals(m.get("a"))) {
            throw new RuntimeException("existing value was overwritten");
        }
    }

    static void testPutIfAbsentNullValue() {
        ConcurrentSkipListMap<String, String> m = new ConcurrentSkipListMap<>();
        try {
            m.putIfAbsent("b", null);
            throw new RuntimeException("expected NullPointerException for null value");
        } catch (NullPointerException e) {
            // expected
        }
        if (m.containsKey("b")) {
            throw new RuntimeException("null value must not be stored");
        }
    }

    static void testPutIfAbsentNullKey() {
        ConcurrentSkipListMap<String, String> m = new ConcurrentSkipListMap<>();
        m.put("a", "1");
        try {
            m.putIfAbsent(null, "x");
            throw new RuntimeException("expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // expected
        }
        if (m.size() != 1) {
            throw new RuntimeException("map changed after rejected null key");
        }
    }
}
