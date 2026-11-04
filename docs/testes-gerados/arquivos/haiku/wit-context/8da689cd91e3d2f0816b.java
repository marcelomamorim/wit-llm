/*
 * @test
 * @summary WITUP-derived behavioral tests for sun.text.IntHashtable.remove(int)
 * @modules java.base/sun.text
 * @run main IntHashtableWitupTest
 */

import sun.text.IntHashtable;

public class IntHashtableWitupTest {

    public static void main(String[] args) {
        testRemoveExistingKeyRestoresDefault();
        testRemoveAbsentKeyIsNoOp();
        testRemoveTwiceIsIdempotent();
        testRemoveKeepsOtherKeys();
        testRemoveUsesCustomDefaultValue();
    }

    static void testRemoveExistingKeyRestoresDefault() {
        IntHashtable h = new IntHashtable();
        h.put(5, 10);
        check(h.size() == 1, "size before remove");
        h.remove(5);
        check(h.size() == 0, "size after remove");
        check(h.isEmpty(), "isEmpty after remove");
        check(h.get(5) == h.getDefaultValue(), "get after remove returns default");
    }

    static void testRemoveAbsentKeyIsNoOp() {
        IntHashtable h = new IntHashtable();
        h.put(1, 100);
        h.remove(999);
        check(h.size() == 1, "size unchanged after removing absent key");
        check(h.get(1) == 100, "existing value preserved after removing absent key");
        check(h.get(999) == h.getDefaultValue(), "absent key returns default");
    }

    static void testRemoveTwiceIsIdempotent() {
        IntHashtable h = new IntHashtable();
        h.put(7, 70);
        h.put(8, 80);
        h.remove(7);
        check(h.size() == 1, "size after first remove");
        h.remove(7);
        check(h.size() == 1, "size unchanged after second remove of same key");
        check(h.get(8) == 80, "other key intact after double remove");
    }

    static void testRemoveKeepsOtherKeys() {
        IntHashtable h = new IntHashtable();
        for (int i = 1; i <= 20; i++) {
            h.put(i, i * 3);
        }
        h.remove(10);
        check(h.size() == 19, "size after removing one of 20 keys");
        check(h.get(10) == h.getDefaultValue(), "removed key returns default");
        for (int i = 1; i <= 20; i++) {
            if (i != 10) {
                check(h.get(i) == i * 3, "other key preserved: " + i);
            }
        }
    }

    static void testRemoveUsesCustomDefaultValue() {
        IntHashtable h = new IntHashtable();
        h.setDefaultValue(-1);
        h.put(3, 33);
        h.remove(3);
        check(h.get(3) == -1, "removed key returns custom default");
        check(h.getDefaultValue() == -1, "default value retained");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
