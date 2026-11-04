/*
 * @test
 * @summary Test sun.text.IntHashtable remove(int key) method behavior
 * @run main IntHashtableWitupTest
 */

public class IntHashtableWitupTest {
    public static void main(String[] args) {
        testRemoveExistingKey();
        testRemoveNonExistingKey();
        testRemoveDoesNotThrowForInvalidKey();
    }

    private static void testRemoveExistingKey() {
        sun.text.IntHashtable table = new sun.text.IntHashtable();
        int key = 12345;
        int value = 67890;
        table.put(key, value);
        if (table.size() != 1) {
            throw new RuntimeException("Setup failed: size should be 1 after put");
        }
        table.remove(key);
        if (table.size() != 0) {
            throw new RuntimeException("Remove failed: size should be 0 after remove");
        }
        int defaultValue = table.getDefaultValue();
        int got = table.get(key);
        if (got != defaultValue) {
            throw new RuntimeException("Remove failed: get(key) should return defaultValue after removal");
        }
    }

    private static void testRemoveNonExistingKey() {
        sun.text.IntHashtable table = new sun.text.IntHashtable();
        int key = 54321;
        int initialSize = table.size();
        table.remove(key); // removing non-existing key should not change size
        if (table.size() != initialSize) {
            throw new RuntimeException("Remove non-existing key changed size");
        }
    }

    private static void testRemoveDoesNotThrowForInvalidKey() {
        sun.text.IntHashtable table = new sun.text.IntHashtable();
        // According to current source, remove does not throw even if key <= DELETED
        // We test that no exception is thrown for such keys
        int[] invalidKeys = {Integer.MIN_VALUE, -1, 0, sun.text.IntHashtable.DELETED, sun.text.IntHashtable.MAX_UNUSED};
        for (int key : invalidKeys) {
            try {
                table.remove(key);
            } catch (Exception e) {
                throw new RuntimeException("Remove threw exception for key " + key + ": " + e);
            }
        }
    }
}
