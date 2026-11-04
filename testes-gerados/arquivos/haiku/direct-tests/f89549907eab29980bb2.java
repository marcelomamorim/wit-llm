/*
 * @test
 * @summary Verifies put behavior of ConcurrentSkipListMap and its submap views, including range checks and null handling
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void testPutOnMap() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        check(m.put(1, "a") == null, "first put should return null");
        check("a".equals(m.put(1, "b")), "second put should return previous value");
        check("b".equals(m.get(1)), "get should observe the latest put");
        check(m.size() == 1, "size should remain 1 after replacing a value");
    }

    static void testPutOnHeadSubmap() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        NavigableMap<Integer, String> head = m.headMap(5);
        check(head.put(2, "x") == null, "put in headMap within range should return null");
        check("x".equals(m.get(2)), "put through headMap should be visible in backing map");
    }

    static void testPutOnSubRangeSubmap() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        NavigableMap<Integer, String> sub = m.subMap(10, true, 20, false);
        check(sub.put(10, "t") == null, "put at inclusive lower bound should succeed");
        check(sub.put(19, "u") == null, "put inside range should succeed");
        check("u".equals(m.get(19)), "value put via subMap should be visible in backing map");

        boolean thrown = false;
        try {
            sub.put(20, "z");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "put at exclusive upper bound should throw IllegalArgumentException");
        check(!m.containsKey(20), "rejected out-of-range put must not add the key");
    }

    static void testPutNullKey() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        boolean thrown = false;
        try {
            m.put(null, "n");
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "put with null key should throw NullPointerException");
        check(m.isEmpty(), "map should remain empty after rejected null key");
    }

    static void testPutNullValue() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        boolean thrown = false;
        try {
            m.put(1, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "put with null value should throw NullPointerException");
        check(!m.containsKey(1), "map should not contain key after rejected null value");
    }

    public static void main(String[] args) {
        testPutOnMap();
        testPutOnHeadSubmap();
        testPutOnSubRangeSubmap();
        testPutNullKey();
        testPutNullValue();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
