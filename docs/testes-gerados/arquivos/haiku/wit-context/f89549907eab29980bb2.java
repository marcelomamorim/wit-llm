/*
 * @test
 * @summary WITUP-derived tests for ConcurrentSkipListMap put with null keys, null values, and SubMap key bounds
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testPutNullKeyThrowsNpe();
        testPutNullValueThrowsNpe();
        testSubMapPutNullKeyThrowsNpe();
        testSubMapPutOutOfRangeKeyThrowsIae();
        testPutReturnsPreviousValue();
    }

    static void testPutNullKeyThrowsNpe() {
        ConcurrentSkipListMap<String, String> m = new ConcurrentSkipListMap<>();
        m.put("a", "1");
        try {
            m.put(null, "v");
            throw new AssertionError("expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }
    }

    static void testPutNullValueThrowsNpe() {
        ConcurrentSkipListMap<String, String> m = new ConcurrentSkipListMap<>();
        try {
            m.put("a", null);
            throw new AssertionError("expected NullPointerException for null value");
        } catch (NullPointerException expected) {
        }
    }

    static void testSubMapPutNullKeyThrowsNpe() {
        ConcurrentSkipListMap<String, String> m = new ConcurrentSkipListMap<>();
        m.put("a", "1");
        ConcurrentNavigableMap<String, String> head = m.headMap("m");
        try {
            head.put(null, "v");
            throw new AssertionError("expected NullPointerException for null key in submap");
        } catch (NullPointerException expected) {
        }
    }

    static void testSubMapPutOutOfRangeKeyThrowsIae() {
        ConcurrentSkipListMap<String, String> m = new ConcurrentSkipListMap<>();
        m.put("a", "1");
        ConcurrentNavigableMap<String, String> head = m.headMap("m");
        try {
            head.put("z", "v");
            throw new AssertionError("expected IllegalArgumentException for out-of-range key");
        } catch (IllegalArgumentException expected) {
        }
        if (m.containsKey("z")) {
            throw new AssertionError("out-of-range key must not be inserted");
        }
    }

    static void testPutReturnsPreviousValue() {
        ConcurrentSkipListMap<String, String> m = new ConcurrentSkipListMap<>();
        if (m.put("a", "1") != null) {
            throw new AssertionError("first put should return null");
        }
        if (!"1".equals(m.put("a", "2"))) {
            throw new AssertionError("second put should return previous value");
        }
        if (!"2".equals(m.get("a"))) {
            throw new AssertionError("value should be updated");
        }
    }
}
