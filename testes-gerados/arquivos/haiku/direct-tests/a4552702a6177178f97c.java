/*
 * @test
 * @summary Verify WeakHashMap values iterator next() returns the mapped values, including null values, and advances correctly
 * @run main WeakHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    // Strong references keep keys reachable so entries are not removed by GC during the test.
    private static final List<Object> KEEP_ALIVE = new ArrayList<>();

    public static void main(String[] args) {
        testSingleValueReturned();
        testAllValuesReturned();
        testNullValueReturnedAsNull();
        testNextAdvancesIterator();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSingleValueReturned() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        Object key = new Object();
        KEEP_ALIVE.add(key);
        map.put(key, "v");

        Iterator<String> it = map.values().iterator();
        check(it.hasNext(), "iterator should have one element");
        check("v".equals(it.next()), "next() should return the mapped value");
        check(!it.hasNext(), "iterator should be exhausted after one element");
    }

    static void testAllValuesReturned() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        String[] values = {"a", "b", "c"};
        for (String v : values) {
            Object key = new Object();
            KEEP_ALIVE.add(key);
            map.put(key, v);
        }

        Set<String> seen = new HashSet<>();
        Iterator<String> it = map.values().iterator();
        int count = 0;
        while (it.hasNext()) {
            seen.add(it.next());
            count++;
        }
        check(count == 3, "expected 3 values but got " + count);
        check(seen.size() == 3, "expected 3 distinct values");
        for (String v : values) {
            check(seen.contains(v), "missing value " + v);
        }
    }

    static void testNullValueReturnedAsNull() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        Object key = new Object();
        KEEP_ALIVE.add(key);
        map.put(key, null);

        Iterator<String> it = map.values().iterator();
        check(it.hasNext(), "iterator should contain the null-valued entry");
        check(it.next() == null, "next() should return null for a null value");
        check(!it.hasNext(), "iterator should be exhausted after null value");
    }

    static void testNextAdvancesIterator() {
        WeakHashMap<Object, Integer> map = new WeakHashMap<>();
        for (int i = 0; i < 4; i++) {
            Object key = new Object();
            KEEP_ALIVE.add(key);
            map.put(key, Integer.valueOf(i));
        }

        Iterator<Integer> it = map.values().iterator();
        int remaining = 4;
        while (remaining > 0) {
            check(it.hasNext(), "hasNext() should be true with " + remaining + " remaining");
            it.next();
            remaining--;
        }
        check(!it.hasNext(), "hasNext() should be false after consuming all values");
    }
}
