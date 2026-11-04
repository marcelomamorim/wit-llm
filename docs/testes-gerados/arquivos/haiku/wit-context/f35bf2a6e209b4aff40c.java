/*
 * @test
 * @summary Behavioral checks for IntHashtable.putInternal on the current checkout
 * @modules java.base/sun.text
 * @run main IntHashtableWitupTest
 */

import sun.text.IntHashtable;

public class IntHashtableWitupTest {

    public static void main(String[] args) {
        testPutInternalNewKeyIsStored();
        testPutInternalNewKeyIncrementsSize();
        testPutInternalExistingKeyUpdatesValueWithoutGrowing();
        testPutInternalMultipleKeys();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPutInternalNewKeyIsStored() {
        IntHashtable table = new IntHashtable();
        table.putInternal(5, 42);
        check(table.get(5) == 42, "putInternal new key: value not retrievable");
    }

    static void testPutInternalNewKeyIncrementsSize() {
        IntHashtable table = new IntHashtable();
        int before = table.size();
        table.putInternal(7, 1);
        check(table.size() == before + 1, "putInternal new key: size not incremented");
    }

    static void testPutInternalExistingKeyUpdatesValueWithoutGrowing() {
        IntHashtable table = new IntHashtable();
        table.putInternal(5, 42);
        int sizeAfterFirst = table.size();
        table.putInternal(5, 7);
        check(table.get(5) == 7, "putInternal existing key: value not updated");
        check(table.size() == sizeAfterFirst, "putInternal existing key: size changed");
    }

    static void testPutInternalMultipleKeys() {
        IntHashtable table = new IntHashtable(10);
        for (int i = 1; i <= 5; i++) {
            table.putInternal(i * 3, i * 10);
        }
        check(table.size() == 5, "putInternal multiple keys: unexpected size " + table.size());
        for (int i = 1; i <= 5; i++) {
            check(table.get(i * 3) == i * 10, "putInternal multiple keys: wrong value for key " + (i * 3));
        }
    }
}
