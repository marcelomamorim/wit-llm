/*
 * @test
 * @summary Behavioral tests for IntHashtable.get on absent, present, removed and rehashed keys
 * @modules java.base/sun.text
 * @run main IntHashtableWitupTest
 */

import sun.text.IntHashtable;

public class IntHashtableWitupTest {

    public static void main(String[] args) {
        testAbsentKeyReturnsDefaultValue();
        testPutThenGet();
        testOverwriteValue();
        testRemoveRestoresDefaultValue();
        testSetDefaultValueAffectsAbsentKeys();
        testManyKeysSurviveRehash();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAbsentKeyReturnsDefaultValue() {
        IntHashtable h = new IntHashtable();
        int def = h.getDefaultValue();
        check(h.get(5) == def, "absent key must return default value");
        check(h.get(-1000) == def, "absent negative key must return default value");
        check(h.get(Integer.MAX_VALUE) == def, "absent MAX_VALUE key must return default value");
    }

    static void testPutThenGet() {
        IntHashtable h = new IntHashtable(10);
        h.put(42, 7);
        check(h.get(42) == 7, "get must return the value stored by put");
        check(h.size() == 1, "size must be 1 after one put");
        check(!h.isEmpty(), "table must not be empty after put");
        check(h.get(43) == h.getDefaultValue(), "other key must still return default");
    }

    static void testOverwriteValue() {
        IntHashtable h = new IntHashtable();
        h.put(1, 100);
        h.put(1, 200);
        check(h.get(1) == 200, "get must return the latest value for the key");
        check(h.size() == 1, "overwriting a key must not change size");
    }

    static void testRemoveRestoresDefaultValue() {
        IntHashtable h = new IntHashtable();
        h.put(9, 55);
        h.remove(9);
        check(h.get(9) == h.getDefaultValue(), "removed key must return default value");
        check(h.size() == 0, "size must be 0 after removing the only key");
        check(h.isEmpty(), "table must be empty after removing the only key");
    }

    static void testSetDefaultValueAffectsAbsentKeys() {
        IntHashtable h = new IntHashtable();
        h.setDefaultValue(-3);
        check(h.getDefaultValue() == -3, "getDefaultValue must reflect setDefaultValue");
        check(h.get(123) == -3, "absent key must return new default value");
        h.put(123, 8);
        check(h.get(123) == 8, "stored value must take precedence over default");
    }

    static void testManyKeysSurviveRehash() {
        IntHashtable h = new IntHashtable();
        final int n = 500;
        for (int i = 0; i < n; i++) {
            h.put(i * 7, i + 1);
        }
        check(h.size() == n, "size must equal number of distinct keys inserted");
        for (int i = 0; i < n; i++) {
            check(h.get(i * 7) == i + 1, "value lost after rehash for key " + (i * 7));
        }
        for (int i = 0; i < n; i += 2) {
            h.remove(i * 7);
        }
        for (int i = 0; i < n; i++) {
            int expected = (i % 2 == 0) ? h.getDefaultValue() : i + 1;
            check(h.get(i * 7) == expected, "wrong value after removals for key " + (i * 7));
        }
    }
}
