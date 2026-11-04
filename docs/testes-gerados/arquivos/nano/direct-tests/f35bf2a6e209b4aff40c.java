/* @test    @summary Test IntHashtable.putInternal method for correct behavior    @run main IntHashtableWitupTest */

import sun.text.IntHashtable;

public class IntHashtableWitupTest {

    public static void main(String[] args) {
        testPutInternalInsertsNewKey();
        testPutInternalUpdatesExistingKey();
        testPutInternalHandlesRehash();
    }

    private static void testPutInternalInsertsNewKey() {
        IntHashtable ht = new IntHashtable();
        int initialSize = ht.size();
        ht.putInternal(42, 100);
        int newSize = ht.size();
        if (newSize != initialSize + 1) {
            throw new AssertionError("Expected size to increase by 1 after insert, but was " + (newSize - initialSize));
        }
        int value = ht.get(42);
        if (value != 100) {
            throw new AssertionError("Expected value 100 for key 42, but got " + value);
        }
    }

    private static void testPutInternalUpdatesExistingKey() {
        IntHashtable ht = new IntHashtable();
        ht.putInternal(55, 200);
        int sizeBefore = ht.size();
        ht.putInternal(55, 300);
        int sizeAfter = ht.size();
        if (sizeBefore != sizeAfter) {
            throw new AssertionError("Size should not change when updating existing key. Before: " + sizeBefore + ", After: " + sizeAfter);
        }
        int value = ht.get(55);
        if (value != 300) {
            throw new AssertionError("Expected updated value 300 for key 55, but got " + value);
        }
    }

    private static void testPutInternalHandlesRehash() {
        IntHashtable ht = new IntHashtable(1); // small initial size to force rehash
        ht.putInternal(1, 10);
        ht.putInternal(2, 20); // may trigger rehash
        int v1 = ht.get(1);
        int v2 = ht.get(2);
        if (v1 != 10 || v2 != 20) {
            throw new AssertionError("Values after rehash are incorrect: key 1 -> " + v1 + ", key 2 -> " + v2);
        }
    }
}
