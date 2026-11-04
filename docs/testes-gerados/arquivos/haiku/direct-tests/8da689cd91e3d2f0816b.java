/*
 * @test
 * @summary Verifies observable behavior of IntHashtable.remove(int) through the public API
 * @modules java.base/sun.text
 * @run main IntHashtableWitupTest
 */

import sun.text.IntHashtable;

public class IntHashtableWitupTest {

    public static void main(String[] args) {
        testRemoveExistingKeyRestoresDefault();
        testRemoveAbsentKeyIsNoOp();
        testRemoveTwiceDecrementsOnce();
        testRemoveOddKeysFromLargeTable();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveExistingKeyRestoresDefault() {
        IntHashtable h = new IntHashtable();
        h.setDefaultValue(-1);
        h.put(10, 100);
        check(h.get(10) == 100, "value before remove");
        check(h.size() == 1, "size before remove");
        h.remove(10);
        check(h.get(10) == -1, "default after remove");
        check(h.size() == 0, "size after remove");
        check(h.isEmpty(), "isEmpty after remove");
    }

    static void testRemoveAbsentKeyIsNoOp() {
        IntHashtable h = new IntHashtable();
        h.setDefaultValue(-1);
        h.put(1, 5);
        h.put(2, 6);
        h.remove(99);
        check(h.size() == 2, "size unchanged after absent remove");
        check(h.get(1) == 5, "key 1 preserved");
        check(h.get(2) == 6, "key 2 preserved");
        check(h.get(99) == -1, "absent key returns default");
    }

    static void testRemoveTwiceDecrementsOnce() {
        IntHashtable h = new IntHashtable();
        h.setDefaultValue(-1);
        h.put(7, 70);
        h.remove(7);
        h.remove(7);
        check(h.size() == 0, "size must not go negative");
        check(h.isEmpty(), "isEmpty after double remove");
        check(h.get(7) == -1, "default after double remove");
    }

    static void testRemoveOddKeysFromLargeTable() {
        IntHashtable h = new IntHashtable();
        h.setDefaultValue(-1);
        for (int i = 1; i <= 50; i++) {
            h.put(i, i * 2);
        }
        check(h.size() == 50, "size after inserts");
        for (int i = 1; i <= 50; i += 2) {
            h.remove(i);
        }
        check(h.size() == 25, "size after removing odd keys");
        for (int i = 1; i <= 50; i++) {
            if (i % 2 == 0) {
                check(h.get(i) == i * 2, "even key preserved");
            } else {
                check(h.get(i) == -1, "odd key removed");
            }
        }
        for (int i = 2; i <= 50; i += 2) {
            h.remove(i);
        }
        check(h.isEmpty(), "empty after removing all keys");
    }
}
