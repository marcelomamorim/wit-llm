/*
 * @test
 * @summary White-box-free observable checks for IntHashtable.putInternal
 * @modules java.base/sun.text
 * @run main IntHashtableWitupTest
 */

import sun.text.IntHashtable;

public class IntHashtableWitupTest {

    public static void main(String[] args) {
        testUpdateExistingKeyViaPutInternal();
        testUpdateExistingKeyDoesNotChangeSize();
        testPutInternalNewKeyReadableViaGet();
        testPutInternalDoesNotChangeDefaultValue();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testUpdateExistingKeyViaPutInternal() {
        IntHashtable table = new IntHashtable();
        table.put(10, 1);
        table.putInternal(10, 5);
        check(table.get(10) == 5,
              "putInternal should overwrite value of existing key, got " + table.get(10));
    }

    static void testUpdateExistingKeyDoesNotChangeSize() {
        IntHashtable table = new IntHashtable();
        table.put(42, 7);
        int sizeBefore = table.size();
        table.putInternal(42, 9);
        check(table.size() == sizeBefore,
              "putInternal on existing key must not change size: before=" + sizeBefore
              + " after=" + table.size());
        check(table.get(42) == 9, "value of existing key should be updated");
    }

    static void testPutInternalNewKeyReadableViaGet() {
        IntHashtable table = new IntHashtable();
        table.putInternal(123, 77);
        check(table.get(123) == 77,
              "value written by putInternal for new key should be readable, got " + table.get(123));
    }

    static void testPutInternalDoesNotChangeDefaultValue() {
        IntHashtable table = new IntHashtable();
        table.setDefaultValue(-1);
        table.putInternal(5, 3);
        check(table.getDefaultValue() == -1,
              "putInternal must not alter the default value");
        check(table.get(5) == 3, "value stored by putInternal should be retrievable");
    }
}
