/*
 * @test
 * @summary Tests the WeakHashMap(Map) copy constructor: copied mappings, null key and value handling, independence from the source map, and NullPointerException for a null source.
 * @run main WeakHashMapWitupTest
 */

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testCopyPreservesMappings() {
        Map<String, Integer> src = new HashMap<>();
        src.put("a", 1);
        src.put("b", 2);
        src.put("c", 3);
        WeakHashMap<String, Integer> w = new WeakHashMap<>(src);
        check(w.size() == 3, "size mismatch: " + w.size());
        check(Integer.valueOf(1).equals(w.get("a")), "value for a");
        check(Integer.valueOf(2).equals(w.get("b")), "value for b");
        check(Integer.valueOf(3).equals(w.get("c")), "value for c");
        check(w.get("zz") == null, "absent key should map to null");
        check(!w.containsKey("zz"), "absent key should not be contained");
        check(!w.isEmpty(), "copy should not be empty");
    }

    static void testEmptySource() {
        Map<String, Integer> src = new HashMap<>();
        WeakHashMap<String, Integer> w = new WeakHashMap<>(src);
        check(w.isEmpty(), "copy of empty map should be empty");
        check(w.size() == 0, "size of copy of empty map should be 0");
        w.put("x", 42);
        check(w.size() == 1, "map should accept entries after empty copy");
        check(Integer.valueOf(42).equals(w.get("x")), "value after put");
    }

    static void testNullKeyAndValue() {
        Map<String, String> src = new HashMap<>();
        src.put(null, "nullKeyValue");
        src.put("x", null);
        WeakHashMap<String, String> w = new WeakHashMap<>(src);
        check(w.size() == 2, "size with null key and null value: " + w.size());
        check(w.containsKey(null), "null key should be copied");
        check("nullKeyValue".equals(w.get(null)), "value for null key");
        check(w.containsKey("x"), "key with null value should be copied");
        check(w.get("x") == null, "null value should be preserved");
    }

    static void testLargeSource() {
        Map<String, Integer> src = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            src.put("k" + i, i);
        }
        WeakHashMap<String, Integer> w = new WeakHashMap<>(src);
        check(w.size() == 1000, "large copy size: " + w.size());
        for (int i = 0; i < 1000; i++) {
            check(Integer.valueOf(i).equals(w.get("k" + i)), "value mismatch at " + i);
        }
    }

    static void testCopyIsIndependentOfSource() {
        Map<String, Integer> src = new HashMap<>();
        src.put("a", 1);
        WeakHashMap<String, Integer> w = new WeakHashMap<>(src);
        w.put("added", 7);
        w.remove("a");
        check(src.size() == 1, "source size changed: " + src.size());
        check(src.containsKey("a"), "source lost key a");
        check(!src.containsKey("added"), "source gained key added");
    }

    static void testNullSourceThrowsNPE() {
        boolean thrown = false;
        try {
            new WeakHashMap<String, Integer>((Map<String, Integer>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null source map should throw NullPointerException");
    }

    public static void main(String[] args) {
        testCopyPreservesMappings();
        testEmptySource();
        testNullKeyAndValue();
        testLargeSource();
        testCopyIsIndependentOfSource();
        testNullSourceThrowsNPE();
    }
}
