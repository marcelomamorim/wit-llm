/*
 * @test
 * @summary Witup-derived tests for ConcurrentSkipListMap.putIfAbsent
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNullValueThrowsNPE();
        testNullKeyWithNonNullValueThrowsNPE();
        testAbsentKeyInserts();
        testPresentKeyKeepsOldValue();
    }

    static void testNullValueThrowsNPE() {
        ConcurrentSkipListMap<String, String> m = new ConcurrentSkipListMap<>();
        m.put("a", "1");
        boolean thrown = false;
        try {
            m.putIfAbsent("a", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            fail("putIfAbsent(key, null) should throw NullPointerException");
        }
        if (!"1".equals(m.get("a"))) {
            fail("map must not change after failed putIfAbsent with null value");
        }
    }

    static void testNullKeyWithNonNullValueThrowsNPE() {
        ConcurrentSkipListMap<String, String> m = new ConcurrentSkipListMap<>();
        boolean thrown = false;
        try {
            m.putIfAbsent(null, "v");
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            fail("putIfAbsent(null, value) should throw NullPointerException");
        }
        if (!m.isEmpty()) {
            fail("map must remain empty after failed putIfAbsent with null key");
        }
    }

    static void testAbsentKeyInserts() {
        ConcurrentSkipListMap<String, String> m = new ConcurrentSkipListMap<>();
        String previous = m.putIfAbsent("a", "1");
        if (previous != null) {
            fail("putIfAbsent on absent key should return null, got " + previous);
        }
        if (!"1".equals(m.get("a"))) {
            fail("value not inserted by putIfAbsent");
        }
    }

    static void testPresentKeyKeepsOldValue() {
        ConcurrentSkipListMap<String, String> m = new ConcurrentSkipListMap<>();
        m.put("a", "1");
        String previous = m.putIfAbsent("a", "2");
        if (!"1".equals(previous)) {
            fail("putIfAbsent on present key should return old value, got " + previous);
        }
        if (!"1".equals(m.get("a"))) {
            fail("putIfAbsent must not replace existing value");
        }
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }
}
