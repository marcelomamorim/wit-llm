/*
 * @test
 * @summary Test ConcurrentSkipListMap ceilingEntry method behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testCeilingEntryEmptyMap();
        testCeilingEntryExactKey();
        testCeilingEntryHigherKey();
        testCeilingEntryNoHigherKey();
        testCeilingEntryNullKey();
    }

    static void testCeilingEntryEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        Map.Entry<Integer, String> e = map.ceilingEntry(10);
        if (e != null) {
            throw new AssertionError("Expected null for ceilingEntry on empty map");
        }
    }

    static void testCeilingEntryExactKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(5, "five");
        map.put(10, "ten");
        map.put(15, "fifteen");
        Map.Entry<Integer, String> e = map.ceilingEntry(10);
        if (e == null || !e.getKey().equals(10) || !"ten".equals(e.getValue())) {
            throw new AssertionError("Expected ceilingEntry to return entry with key 10 and value 'ten'");
        }
    }

    static void testCeilingEntryHigherKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(5, "five");
        map.put(10, "ten");
        map.put(15, "fifteen");
        Map.Entry<Integer, String> e = map.ceilingEntry(7);
        if (e == null || !e.getKey().equals(10) || !"ten".equals(e.getValue())) {
            throw new AssertionError("Expected ceilingEntry to return entry with key 10 and value 'ten' for key 7");
        }
    }

    static void testCeilingEntryNoHigherKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(5, "five");
        map.put(10, "ten");
        map.put(15, "fifteen");
        Map.Entry<Integer, String> e = map.ceilingEntry(20);
        if (e != null) {
            throw new AssertionError("Expected null for ceilingEntry with key greater than any map key");
        }
    }

    static void testCeilingEntryNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.ceilingEntry(null);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
