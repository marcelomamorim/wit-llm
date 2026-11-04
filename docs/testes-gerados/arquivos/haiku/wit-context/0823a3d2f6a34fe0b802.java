/*
 * @test
 * @summary Behavioral tests for the Hashtable(Map) constructor
 * @run main HashtableWitupTest
 */

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testCopiesEntries();
        testEmptySource();
        testNullSourceThrowsNPE();
        testCopyIsIndependentOfSource();
        testLargeSource();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testCopiesEntries() {
        Map<String, Integer> src = new HashMap<>();
        src.put("one", 1);
        src.put("two", 2);
        src.put("three", 3);
        Hashtable<String, Integer> h = new Hashtable<>(src);
        check(h.size() == 3, "size should equal source size");
        check(h.get("one") == 1, "value for 'one' not copied");
        check(h.get("two") == 2, "value for 'two' not copied");
        check(h.get("three") == 3, "value for 'three' not copied");
        check(h.equals(src), "copy should be equal to source map");
    }

    static void testEmptySource() {
        Map<String, Integer> src = new HashMap<>();
        Hashtable<String, Integer> h = new Hashtable<>(src);
        check(h.isEmpty(), "copy of empty map should be empty");
        check(h.size() == 0, "size of copy of empty map should be 0");
        h.put("k", 7);
        check(h.size() == 1 && h.get("k") == 7, "table should be usable after empty copy");
    }

    static void testNullSourceThrowsNPE() {
        boolean thrown = false;
        try {
            new Hashtable<String, Integer>((Map<String, Integer>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null source map should cause NullPointerException");
    }

    static void testCopyIsIndependentOfSource() {
        Map<String, Integer> src = new HashMap<>();
        src.put("a", 1);
        Hashtable<String, Integer> h = new Hashtable<>(src);
        src.put("b", 2);
        src.put("a", 100);
        check(h.size() == 1, "later source changes must not change copy size");
        check(h.get("a") == 1, "later source changes must not change copied value");
        check(!h.containsKey("b"), "later source additions must not appear in copy");
    }

    static void testLargeSource() {
        Map<Integer, Integer> src = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            src.put(i, i * 2);
        }
        Hashtable<Integer, Integer> h = new Hashtable<>(src);
        check(h.size() == 1000, "size should be 1000 for large source");
        for (int i = 0; i < 1000; i++) {
            Integer v = h.get(i);
            check(v != null && v == i * 2, "missing or wrong value for key " + i);
        }
    }
}
