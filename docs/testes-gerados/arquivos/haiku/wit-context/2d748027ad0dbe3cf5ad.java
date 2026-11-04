/*
 * @test
 * @summary Witup-derived tests for Hashtable.replace(K,V)
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testReplaceExistingKeyReturnsOldValue();
        testReplaceAbsentKeyReturnsNullAndDoesNotInsert();
        testReplaceNullValueThrowsNPEAndKeepsState();
        testReplaceNullKeyThrowsNPE();
    }

    static void testReplaceExistingKeyReturnsOldValue() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("one", 1);
        Integer old = h.replace("one", 11);
        check(Integer.valueOf(1).equals(old), "old value expected 1 but got " + old);
        check(Integer.valueOf(11).equals(h.get("one")), "new value expected 11 but got " + h.get("one"));
        check(h.size() == 1, "size expected 1 but got " + h.size());
    }

    static void testReplaceAbsentKeyReturnsNullAndDoesNotInsert() {
        Hashtable<String, Integer> h = new Hashtable<>();
        Integer old = h.replace("missing", 5);
        check(old == null, "expected null for absent key but got " + old);
        check(h.get("missing") == null, "absent key must not be inserted");
        check(h.size() == 0, "size expected 0 but got " + h.size());
    }

    static void testReplaceNullValueThrowsNPEAndKeepsState() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        try {
            h.replace("a", null);
            throw new RuntimeException("expected NullPointerException for null value");
        } catch (NullPointerException e) {
            // expected
        }
        check(Integer.valueOf(1).equals(h.get("a")), "value must be unchanged after failed replace");
        check(h.size() == 1, "size expected 1 but got " + h.size());
    }

    static void testReplaceNullKeyThrowsNPE() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        try {
            h.replace(null, 2);
            throw new RuntimeException("expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // expected
        }
        check(h.size() == 1, "size expected 1 but got " + h.size());
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
