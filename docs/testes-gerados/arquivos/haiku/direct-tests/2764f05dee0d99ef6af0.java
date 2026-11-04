/*
 * @test
 * @summary Behavioral tests for Hashtable.put based on its source code
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testNewKeyReturnsNull();
        testReplaceReturnsOldValue();
        testReplaceDoesNotChangeSize();
        testNullValueThrowsAndKeepsOldValue();
        testNullKeyThrows();
        testCollidingKeysAreStoredSeparately();
        testNullValueOnNewKeyThrowsAndDoesNotInsert();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNewKeyReturnsNull() {
        Hashtable<String, Integer> h = new Hashtable<>();
        Integer previous = h.put("one", 1);
        check(previous == null, "put of new key must return null");
        check(h.get("one") == 1, "value must be retrievable after put");
    }

    static void testReplaceReturnsOldValue() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("k", 1);
        Integer previous = h.put("k", 2);
        check(previous != null && previous == 1, "put of existing key must return old value");
        check(h.get("k") == 2, "put of existing key must update value");
    }

    static void testReplaceDoesNotChangeSize() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("k", 1);
        h.put("k", 2);
        check(h.size() == 1, "replacing a key must not increase size");
    }

    static void testNullValueThrowsAndKeepsOldValue() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("k", 1);
        boolean thrown = false;
        try {
            h.put("k", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "put with null value must throw NullPointerException");
        check(h.get("k") == 1, "failed put must not change existing value");
    }

    static void testNullValueOnNewKeyThrowsAndDoesNotInsert() {
        Hashtable<String, Integer> h = new Hashtable<>();
        boolean thrown = false;
        try {
            h.put("new", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "put with null value must throw NullPointerException");
        check(h.size() == 0, "failed put must not insert an entry");
    }

    static void testNullKeyThrows() {
        Hashtable<String, Integer> h = new Hashtable<>();
        boolean thrown = false;
        try {
            h.put(null, 1);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "put with null key must throw NullPointerException");
        check(h.size() == 0, "failed put with null key must not insert an entry");
    }

    static void testCollidingKeysAreStoredSeparately() {
        // "Aa" and "BB" have the same String hashCode
        Hashtable<String, Integer> h = new Hashtable<>();
        check(h.put("Aa", 1) == null, "first colliding key must return null");
        check(h.put("BB", 2) == null, "second colliding key must return null");
        check(h.get("Aa") == 1, "first colliding key must keep its value");
        check(h.get("BB") == 2, "second colliding key must keep its value");
        check(h.size() == 2, "colliding keys must produce two entries");
        check(h.put("Aa", 3) == 1, "replacing colliding key must return its old value");
        check(h.get("BB") == 2, "replacing one colliding key must not affect the other");
    }
}
