/*
 * @test
 * @summary Black-box tests for the observable behavior of IntHashtable.put
 * @modules java.base/sun.text
 * @run main IntHashtableWitupTest
 */

import sun.text.IntHashtable;

public class IntHashtableWitupTest {

    public static void main(String[] args) {
        testNewTableIsEmpty();
        testPutSingleKey();
        testPutOverwritesValue();
        testPutZeroNegativeAndMaxKeys();
        testPutManyKeysTriggersRehash();
        testPutReusesDeletedSlot();
        testMissingKeyReturnsDefaultValue();
        testRemovedKeyReturnsDefaultValue();
        testLargeInitialSizeConstructor();
    }

    static void testNewTableIsEmpty() {
        IntHashtable t = new IntHashtable();
        check(t.size() == 0, "new table size must be 0");
        check(t.isEmpty(), "new table must be empty");
    }

    static void testPutSingleKey() {
        IntHashtable t = new IntHashtable();
        t.put(5, 42);
        check(t.size() == 1, "size after one put must be 1");
        check(!t.isEmpty(), "table must not be empty after put");
        check(t.get(5) == 42, "get must return stored value");
    }

    static void testPutOverwritesValue() {
        IntHashtable t = new IntHashtable();
        t.put(5, 1);
        t.put(5, 2);
        check(t.size() == 1, "overwriting a key must not change size");
        check(t.get(5) == 2, "second put must replace value");
    }

    static void testPutZeroNegativeAndMaxKeys() {
        IntHashtable t = new IntHashtable();
        t.put(0, 10);
        t.put(-1, 20);
        t.put(Integer.MAX_VALUE, 30);
        check(t.size() == 3, "three distinct keys must give size 3");
        check(t.get(0) == 10, "value for key 0");
        check(t.get(-1) == 20, "value for key -1");
        check(t.get(Integer.MAX_VALUE) == 30, "value for MAX_VALUE key");
    }

    static void testPutManyKeysTriggersRehash() {
        IntHashtable t = new IntHashtable(1);
        int n = 1000;
        for (int i = 0; i < n; i++) {
            t.put(i * 3, i + 1);
        }
        check(t.size() == n, "size must equal number of distinct keys");
        for (int i = 0; i < n; i++) {
            check(t.get(i * 3) == i + 1, "value lost after growth at index " + i);
        }
    }

    static void testPutReusesDeletedSlot() {
        IntHashtable t = new IntHashtable();
        t.put(1, 1);
        t.put(2, 2);
        t.remove(1);
        check(t.size() == 1, "size after remove must be 1");
        t.put(1, 5);
        check(t.size() == 2, "re-inserting removed key must increase size");
        check(t.get(1) == 5, "re-inserted key must have new value");
        check(t.get(2) == 2, "other key must be unaffected");
    }

    static void testMissingKeyReturnsDefaultValue() {
        IntHashtable t = new IntHashtable();
        t.setDefaultValue(123);
        check(t.get(77) == 123, "missing key must return default value");
    }

    static void testRemovedKeyReturnsDefaultValue() {
        IntHashtable t = new IntHashtable();
        t.setDefaultValue(-7);
        t.put(9, 99);
        t.remove(9);
        check(t.get(9) == -7, "removed key must return default value");
        check(t.size() == 0, "size after removing only key must be 0");
    }

    static void testLargeInitialSizeConstructor() {
        IntHashtable t = new IntHashtable(100);
        for (int i = 0; i < 50; i++) {
            t.put(i, i * 2);
        }
        check(t.size() == 50, "size must be 50");
        for (int i = 0; i < 50; i++) {
            check(t.get(i) == i * 2, "value mismatch for key " + i);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
