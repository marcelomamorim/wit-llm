/* @test    @summary Test removal of existing key in IntHashtable    @run main IntHashtableWitupTest */
/* @modules java.base */

import sun.text.IntHashtable;

public class IntHashtableWitupTest {

    public static void main(String[] args) {
        testRemoveExistingKey();
        testRemoveNonExistingKey();
        testRemoveMultipleKeys();
        testRemoveDefaultValue();
    }

    static void testRemoveExistingKey() {
        IntHashtable ht = new IntHashtable();
        ht.put(10, 100);
        int initialSize = ht.size();
        ht.remove(10);
        assert ht.size() == initialSize - 1 : "Size should decrease after removal";
        int defaultVal = ht.getDefaultValue();
        int value = ht.get(10);
        assert value == defaultVal : "Removed key should return default value";
    }

    static void testRemoveNonExistingKey() {
        IntHashtable ht = new IntHashtable();
        int initialSize = ht.size();
        ht.remove(999);
        assert ht.size() == initialSize : "Size should not change when removing non-existing key";
        int defaultVal = ht.getDefaultValue();
        int value = ht.get(999);
        assert value == defaultVal : "Non-existing key should return default value";
    }

    static void testRemoveMultipleKeys() {
        IntHashtable ht = new IntHashtable(5);
        ht.put(1, 11);
        ht.put(2, 22);
        ht.put(3, 33);

        ht.remove(2);
        ht.remove(3);

        int defaultVal = ht.getDefaultValue();
        assert ht.get(1) != defaultVal : "Key 1 should not be removed";
        assert ht.get(2) == defaultVal : "Key 2 should be removed";
        assert ht.get(3) == defaultVal : "Key 3 should be removed";
        assert ht.size() == 1 : "Only one key should remain";
    }

    static void testRemoveDefaultValue() {
        IntHashtable ht = new IntHashtable();
        ht.setDefaultValue(9999);
        ht.put(5, 55);
        ht.remove(5);
        int defaultVal = ht.getDefaultValue();
        int value = ht.get(5);
        assert value == defaultVal : "After setting default and removing key, get should return new default";
    }
}
