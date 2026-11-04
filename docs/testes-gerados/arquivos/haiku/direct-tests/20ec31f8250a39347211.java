/*
 * @test
 * @summary Verifies observable behavior of Hashtable.containsValue for empty, populated, removed and type-mismatched values
 * @run main HashtableWitupTest
 */

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testEmptyTable();
        testPresentAndAbsentValues();
        testValueAfterRemove();
        testKeysAreNotValues();
        testCopyConstructorPreservesValues();
        testEqualsBasedMatching();
    }

    private static void testEmptyTable() {
        Hashtable<String, Integer> h = new Hashtable<>();
        check(!h.containsValue(1), "empty table must not contain 1");
        check(!h.containsValue("x"), "empty table must not contain a String");
    }

    private static void testPresentAndAbsentValues() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("one", 1);
        h.put("two", 2);
        check(h.containsValue(1), "value 1 should be found");
        check(h.containsValue(2), "value 2 should be found");
        check(!h.containsValue(3), "value 3 should not be found");
    }

    private static void testValueAfterRemove() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("one", 1);
        h.put("two", 2);
        h.remove("one");
        check(!h.containsValue(1), "removed value must not be found");
        check(h.containsValue(2), "remaining value must still be found");
    }

    private static void testKeysAreNotValues() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("two", 2);
        check(!h.containsValue("two"), "a key must not be matched as a value");
    }

    private static void testCopyConstructorPreservesValues() {
        Map<String, Integer> source = new HashMap<>();
        source.put("a", 10);
        source.put("b", 20);
        Hashtable<String, Integer> h = new Hashtable<>(source);
        check(h.containsValue(10), "copied value 10 should be found");
        check(h.containsValue(20), "copied value 20 should be found");
        check(!h.containsValue(30), "absent value 30 should not be found");
    }

    private static void testEqualsBasedMatching() {
        Hashtable<String, Object> h = new Hashtable<>();
        h.put("x", 100L);
        check(h.containsValue(Long.valueOf(100L)), "equal Long value should be found");
        check(!h.containsValue(Integer.valueOf(100)), "Integer 100 must not equal Long 100");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
