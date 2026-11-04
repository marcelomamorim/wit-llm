/*
 * @test
 * @summary Verifies Hashtable.remove(Object, Object) null-value rejection, null-key behavior, and conditional removal
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testNullValueThrowsNpe();
        testNullKeyThrowsNpe();
        testRemovesMatchingPair();
        testAbsentKeyReturnsFalse();
        testValueMismatchKeepsEntry();
        testEmptyTableReturnsFalse();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullValueThrowsNpe() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        boolean thrown = false;
        try {
            h.remove("a", null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "remove(key, null) must throw NullPointerException");
        check(h.containsKey("a") && h.get("a") == 1, "entry must remain after NPE");
    }

    static void testNullKeyThrowsNpe() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        boolean thrown = false;
        try {
            h.remove(null, 1);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "remove(null, value) must throw NullPointerException");
        check(h.size() == 1, "size must be unchanged after NPE");
    }

    static void testRemovesMatchingPair() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("one", 1);
        h.put("two", 2);
        check(h.remove("one", 1) == true, "matching pair must be removed");
        check(!h.containsKey("one"), "removed key must no longer be present");
        check(h.size() == 1, "size must decrease by one");
        check(h.get("two") == 2, "other entries must be preserved");
    }

    static void testAbsentKeyReturnsFalse() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("one", 1);
        check(h.remove("missing", 1) == false, "absent key must return false");
        check(h.size() == 1, "size must be unchanged for absent key");
    }

    static void testValueMismatchKeepsEntry() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("one", 1);
        check(h.remove("one", 2) == false, "value mismatch must return false");
        check(h.containsKey("one") && h.get("one") == 1, "entry must remain when value differs");
    }

    static void testEmptyTableReturnsFalse() {
        Hashtable<String, Integer> h = new Hashtable<>();
        check(h.remove("any", 1) == false, "empty table must return false");
        check(h.isEmpty(), "empty table must remain empty");
    }
}
