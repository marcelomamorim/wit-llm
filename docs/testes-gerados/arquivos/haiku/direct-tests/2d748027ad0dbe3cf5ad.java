/*
 * @test
 * @summary Tests observable behavior of Hashtable.replace(K, V)
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.HashMap;
import java.util.Map;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testReplaceExistingKeyReturnsOldValue();
        testReplaceExistingKeyUpdatesValue();
        testReplaceAbsentKeyReturnsNullAndDoesNotInsert();
        testReplaceOnEmptyTableReturnsNull();
        testReplaceNullValueThrowsNPE();
        testReplaceNullKeyThrowsNPE();
        testReplaceOnCopiedTable();
        System.out.println("All Hashtable.replace tests passed");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReplaceExistingKeyReturnsOldValue() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("one", 1);
        Integer old = table.replace("one", 11);
        check(old != null && old.intValue() == 1, "replace should return previous value");
    }

    static void testReplaceExistingKeyUpdatesValue() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("one", 1);
        table.replace("one", 11);
        check(table.get("one") != null && table.get("one").intValue() == 11,
              "replace should update stored value");
        check(table.size() == 1, "replace on existing key must not change size");
    }

    static void testReplaceAbsentKeyReturnsNullAndDoesNotInsert() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("one", 1);
        Integer old = table.replace("two", 2);
        check(old == null, "replace on absent key should return null");
        check(!table.containsKey("two"), "replace on absent key must not insert");
        check(table.size() == 1, "size must be unchanged for absent key");
    }

    static void testReplaceOnEmptyTableReturnsNull() {
        Hashtable<String, Integer> table = new Hashtable<>();
        Integer old = table.replace("x", 5);
        check(old == null, "replace on empty table should return null");
        check(table.isEmpty(), "replace on empty table must not insert");
    }

    static void testReplaceNullValueThrowsNPE() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("one", 1);
        boolean thrown = false;
        try {
            table.replace("one", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "replace with null value should throw NullPointerException");
        check(table.get("one") != null && table.get("one").intValue() == 1,
              "failed replace must not modify existing value");
    }

    static void testReplaceNullKeyThrowsNPE() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("one", 1);
        boolean thrown = false;
        try {
            table.replace(null, 2);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "replace with null key should throw NullPointerException");
        check(table.size() == 1, "failed replace must not change size");
    }

    static void testReplaceOnCopiedTable() {
        Map<String, Integer> source = new HashMap<>();
        source.put("a", 1);
        source.put("b", 2);
        Hashtable<String, Integer> table = new Hashtable<>(source);
        Integer old = table.replace("b", 20);
        check(old != null && old.intValue() == 2, "replace should return old value on copied table");
        check(table.get("b") != null && table.get("b").intValue() == 20,
              "replace should update copied table entry");
        check(table.size() == 2, "size must remain 2 after replace");
    }
}
