/*
 * @test
 * @summary Verify WeakHashMap(Map) copies entries, supports null key and value, is independent of its source, and rejects a null source
 * @run main WeakHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testCopiesAllEntries();
        testEmptySource();
        testNullKeyAndValue();
        testCopyIsIndependentOfSource();
        testLargeSource();
        testNullSourceThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testCopiesAllEntries() {
        Map<String, Integer> src = new HashMap<>();
        src.put("a", 1);
        src.put("b", 2);
        src.put("c", 3);
        WeakHashMap<String, Integer> m = new WeakHashMap<>(src);
        check(m.size() == 3, "size should be 3");
        check(Integer.valueOf(1).equals(m.get("a")), "a should map to 1");
        check(Integer.valueOf(2).equals(m.get("b")), "b should map to 2");
        check(Integer.valueOf(3).equals(m.get("c")), "c should map to 3");
        check(!m.containsKey("d"), "d should be absent");
    }

    static void testEmptySource() {
        Map<String, String> src = new HashMap<>();
        WeakHashMap<String, String> m = new WeakHashMap<>(src);
        check(m.size() == 0, "copy of empty map should be empty");
        check(m.isEmpty(), "copy of empty map should report isEmpty");
        m.put("x", "y");
        check(m.size() == 1, "map should accept puts after copy");
        check("y".equals(m.get("x")), "put value should be retrievable");
    }

    static void testNullKeyAndValue() {
        Map<String, String> src = new HashMap<>();
        src.put(null, "nullKey");
        src.put("k", null);
        WeakHashMap<String, String> m = new WeakHashMap<>(src);
        check(m.size() == 2, "size should be 2 with null key and null value");
        check("nullKey".equals(m.get(null)), "null key should map to nullKey");
        check(m.containsKey("k"), "k should be present");
        check(m.get("k") == null, "k should map to null");
        check(m.containsValue(null), "null value should be present");
    }

    static void testCopyIsIndependentOfSource() {
        Map<String, Integer> src = new HashMap<>();
        src.put("a", 1);
        WeakHashMap<String, Integer> m = new WeakHashMap<>(src);
        src.put("b", 2);
        src.remove("a");
        check(m.size() == 1, "copy size should not follow source changes");
        check(m.containsKey("a"), "copy should still contain a");
        check(!m.containsKey("b"), "copy should not contain later source additions");
    }

    static void testLargeSource() {
        List<String> keys = new ArrayList<>();
        Map<String, Integer> src = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            String k = new String("key" + i);
            keys.add(k);
            src.put(k, i);
        }
        WeakHashMap<String, Integer> m = new WeakHashMap<>(src);
        check(m.size() == 1000, "size should be 1000");
        for (int i = 0; i < keys.size(); i++) {
            check(Integer.valueOf(i).equals(m.get(keys.get(i))),
                  "value mismatch for key " + i);
        }
    }

    static void testNullSourceThrowsNPE() {
        try {
            new WeakHashMap<String, String>((Map<String, String>) null);
            throw new RuntimeException("expected NullPointerException for null source");
        } catch (NullPointerException expected) {
            // expected: m.size() is evaluated on the null argument
        }
    }
}
