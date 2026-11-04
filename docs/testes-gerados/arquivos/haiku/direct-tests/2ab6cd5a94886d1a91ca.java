/*
 * @test
 * @summary Verifies the ConcurrentHashMap(Map) constructor copies entries, starts empty for an empty source, is independent of later source changes, and yields a mutable map.
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testCopiesEntries();
        testEmptySource();
        testIndependentFromSource();
        testResultIsMutable();
        testLargeSource();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testCopiesEntries() {
        Map<Integer, Integer> src = new HashMap<>();
        src.put(1, 10);
        src.put(2, 20);
        src.put(3, 30);
        ConcurrentHashMap<Integer, Integer> m = new ConcurrentHashMap<>(src);
        check(m.size() == 3, "size should be 3");
        check(m.get(1) == 10, "get(1) should be 10");
        check(m.get(2) == 20, "get(2) should be 20");
        check(m.get(3) == 30, "get(3) should be 30");
        check(m.get(4) == null, "absent key should map to null");
        check(m.containsKey(2), "containsKey(2) should be true");
    }

    static void testEmptySource() {
        Map<Integer, Integer> src = new HashMap<>();
        ConcurrentHashMap<Integer, Integer> m = new ConcurrentHashMap<>(src);
        check(m.isEmpty(), "map copied from empty source should be empty");
        check(m.size() == 0, "size should be 0");
        check(m.get(1) == null, "get on empty copy should return null");
    }

    static void testIndependentFromSource() {
        Map<Integer, Integer> src = new HashMap<>();
        src.put(1, 100);
        ConcurrentHashMap<Integer, Integer> m = new ConcurrentHashMap<>(src);
        src.put(2, 200);
        src.put(1, 999);
        src.remove(1);
        check(m.size() == 1, "later source changes must not affect copy size");
        check(m.get(1) == 100, "later source updates must not affect copied value");
        check(m.get(2) == null, "later source additions must not appear in copy");
    }

    static void testResultIsMutable() {
        Map<Integer, Integer> src = new HashMap<>();
        src.put(1, 1);
        ConcurrentHashMap<Integer, Integer> m = new ConcurrentHashMap<>(src);
        m.put(5, 50);
        check(m.get(5) == 50, "put on copied map should be visible");
        m.remove(1);
        check(m.get(1) == null, "remove on copied map should take effect");
        check(m.size() == 1, "size should reflect put and remove");
    }

    static void testLargeSource() {
        Map<Integer, Integer> src = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            src.put(i, i * 2);
        }
        ConcurrentHashMap<Integer, Integer> m = new ConcurrentHashMap<>(src);
        check(m.size() == 1000, "size should be 1000 for large source");
        for (int i = 0; i < 1000; i++) {
            Integer v = m.get(i);
            check(v != null && v == i * 2, "value mismatch at key " + i);
        }
    }
}
