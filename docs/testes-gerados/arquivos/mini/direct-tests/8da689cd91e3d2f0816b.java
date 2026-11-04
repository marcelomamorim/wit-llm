/*
 * @test
 * @summary Test sun.text.IntHashtable remove(int key) method behavior
 * @run main IntHashtableWitupTest
 */

public class IntHashtableWitupTest {
    public static void main(String[] args) {
        testRemoveExistingKey();
        testRemoveNonExistingKey();
        testRemoveTriggersRehash();
    }

    private static void testRemoveExistingKey() {
        sun.text.IntHashtable ht = new sun.text.IntHashtable();
        ht.put(10, 100);
        int sizeBefore = ht.size();
        int valBefore = ht.get(10);
        if (valBefore != 100) {
            throw new RuntimeException("Expected value 100 for key 10 before removal, got " + valBefore);
        }
        ht.remove(10);
        int valAfter = ht.get(10);
        int sizeAfter = ht.size();
        if (valAfter == 100) {
            throw new RuntimeException("Value for key 10 should be default after removal");
        }
        if (sizeAfter != sizeBefore - 1) {
            throw new RuntimeException("Size should decrease by 1 after removal");
        }
    }

    private static void testRemoveNonExistingKey() {
        sun.text.IntHashtable ht = new sun.text.IntHashtable();
        ht.put(20, 200);
        int sizeBefore = ht.size();
        ht.remove(9999); // key not present
        int sizeAfter = ht.size();
        if (sizeAfter != sizeBefore) {
            throw new RuntimeException("Size should not change when removing non-existing key");
        }
        int val = ht.get(20);
        if (val != 200) {
            throw new RuntimeException("Value for existing key 20 should remain unchanged");
        }
    }

    private static void testRemoveTriggersRehash() {
        // Create a small table to force rehash on removal
        sun.text.IntHashtable ht = new sun.text.IntHashtable(1);
        // Insert multiple keys to increase count
        for (int i = 0; i < 10; i++) {
            ht.put(i, i * 10);
        }
        int sizeBefore = ht.size();
        // Remove keys to drop below lowWaterMark and trigger rehash
        for (int i = 0; i < 10; i++) {
            ht.remove(i);
            if (ht.size() != sizeBefore - (i + 1)) {
                throw new RuntimeException("Size mismatch after removal of key " + i);
            }
        }
        if (!ht.isEmpty()) {
            throw new RuntimeException("Hashtable should be empty after removing all keys");
        }
    }
}
