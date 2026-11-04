/*
 * @test
 * @summary Witup-derived tests for Hashtable.put null value and null key handling
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testNullValueThrowsNpe();
        testNullValueDoesNotModifyTable();
        testNullKeyThrowsNpe();
        testNewKeyReturnsNull();
        testReplaceReturnsOldValue();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullValueThrowsNpe() {
        Hashtable<String, Integer> table = new Hashtable<>();
        boolean thrown = false;
        try {
            table.put("one", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "put with null value must throw NullPointerException");
    }

    static void testNullValueDoesNotModifyTable() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("a", 1);
        boolean thrown = false;
        try {
            table.put("a", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "put with null value must throw NullPointerException");
        check(table.size() == 1, "failed put must not change size");
        check(Integer.valueOf(1).equals(table.get("a")), "failed put must not change existing mapping");
    }

    static void testNullKeyThrowsNpe() {
        Hashtable<String, Integer> table = new Hashtable<>();
        boolean thrown = false;
        try {
            table.put(null, 1);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "put with null key must throw NullPointerException");
        check(table.isEmpty(), "failed put with null key must not add an entry");
    }

    static void testNewKeyReturnsNull() {
        Hashtable<String, Integer> table = new Hashtable<>();
        Integer previous = table.put("k", 1);
        check(previous == null, "put of new key must return null");
        check(table.size() == 1, "put of new key must increase size");
    }

    static void testReplaceReturnsOldValue() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("k", 1);
        Integer old = table.put("k", 2);
        check(Integer.valueOf(1).equals(old), "put of existing key must return previous value");
        check(Integer.valueOf(2).equals(table.get("k")), "put of existing key must replace value");
        check(table.size() == 1, "replacing a value must not change size");
    }
}
