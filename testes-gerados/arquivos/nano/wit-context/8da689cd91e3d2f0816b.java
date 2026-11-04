/*
 * @test
 * @summary Test for sun.text.IntHashtable.remove(int)
 * @run main IntHashtableWitupTest
 */

import sun.text.IntHashtable;

public class IntHashtableWitupTest {

    public static void main(String[] args) {
        testRemoveExistingKey();
        testRemoveNonExistingKey();
        testRemoveDeletedKey();
    }

    private static void testRemoveExistingKey() {
        IntHashtable ht = new IntHashtable();
        ht.put(10, 100);
        int initialSize = ht.size();
        ht.remove(10);
        assert ht.size() == initialSize - 1 : "Removing existing key should decrease size";
        int defaultVal = ht.getDefaultValue();
        int value = ht.get(10);
        assert value == defaultVal : "Removed key should return default value";
    }

    private static void testRemoveNonExistingKey() {
        IntHashtable ht = new IntHashtable();
        int initialSize = ht.size();
        ht.remove(999);
        assert ht.size() == initialSize : "Removing non-existing key should not change size";
        int defaultVal = ht.getDefaultValue();
        int value = ht.get(999);
        assert value == defaultVal : "Non-existing key should return default value";
    }

    private static void testRemoveDeletedKey() {
        IntHashtable ht = new IntHashtable();
        ht.put(20, 200);
        ht.remove(20);
        int sizeAfterRemoval = ht.size();
        ht.remove(20); // removing again
        assert ht.size() == sizeAfterRemoval : "Removing already deleted key should not change size";
        int defaultVal = ht.getDefaultValue();
        int value = ht.get(20);
        assert value == defaultVal : "Deleted key should return default value";
    }
}
