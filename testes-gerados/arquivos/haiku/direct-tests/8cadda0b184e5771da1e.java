/*
 * @test
 * @summary Verifies observable behavior of Hashtable.replace(K, V, V)
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        replaceWhenValueMatches();
        replaceWhenValueDoesNotMatch();
        replaceWhenKeyAbsent();
        replaceOnEmptyTable();
        nullOldValueThrows();
        nullNewValueThrows();
        nullKeyThrows();
    }

    static void replaceWhenValueMatches() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        check(h.replace("a", 1, 2), "expected true when old value matches");
        check(h.get("a").intValue() == 2, "value was not replaced");
        check(h.size() == 1, "size changed unexpectedly");
    }

    static void replaceWhenValueDoesNotMatch() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        check(!h.replace("a", 5, 9), "expected false when old value differs");
        check(h.get("a").intValue() == 1, "value changed despite mismatch");
    }

    static void replaceWhenKeyAbsent() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        check(!h.replace("x", 1, 2), "expected false for absent key");
        check(!h.containsKey("x"), "absent key was inserted");
        check(h.get("a").intValue() == 1, "other entry changed");
    }

    static void replaceOnEmptyTable() {
        Hashtable<String, Integer> h = new Hashtable<>();
        check(!h.replace("a", 1, 2), "expected false on empty table");
        check(h.isEmpty(), "empty table was modified");
    }

    static void nullOldValueThrows() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        expectNpe(() -> h.replace("a", null, 2), "null oldValue must throw NullPointerException");
        check(h.get("a").intValue() == 1, "value changed after NPE");
    }

    static void nullNewValueThrows() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        expectNpe(() -> h.replace("a", 1, null), "null newValue must throw NullPointerException");
        check(h.get("a").intValue() == 1, "value changed after NPE");
    }

    static void nullKeyThrows() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        expectNpe(() -> h.replace(null, 1, 2), "null key must throw NullPointerException");
        check(h.get("a").intValue() == 1, "value changed after NPE");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectNpe(Runnable r, String message) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException(message);
    }
}
