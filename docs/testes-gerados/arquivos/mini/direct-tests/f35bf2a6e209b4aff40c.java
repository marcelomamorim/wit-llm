/*
 * @test
 * @summary Test sun.text.IntHashtable.putInternal method behavior
 * @run main IntHashtableWitupTest
 */

public class IntHashtableWitupTest {

    public static void main(String[] args) {
        IntHashtableWitupTest test = new IntHashtableWitupTest();
        test.testPutInternalNewKey();
        test.testPutInternalOverwriteValue();
    }

    // Test that putInternal inserts a new key and increments count
    public void testPutInternalNewKey() {
        sun.text.IntHashtable ht = new sun.text.IntHashtable();
        int initialCount = ht.size();
        int key = 12345;
        int value = 67890;

        // Use putInternal to add a new key
        ht.putInternal(key, value);

        // After insertion, size should increase by 1
        int newCount = ht.size();
        if (newCount != initialCount + 1) {
            throw new RuntimeException("Count did not increase after putInternal for new key");
        }

        // The value for the key should be the one inserted
        int retrieved = ht.get(key);
        if (retrieved != value) {
            throw new RuntimeException("Value retrieved does not match value inserted by putInternal");
        }
    }

    // Test that putInternal overwrites value for existing key without increasing count
    public void testPutInternalOverwriteValue() {
        sun.text.IntHashtable ht = new sun.text.IntHashtable();
        int key = 54321;
        int initialValue = 11111;
        int newValue = 22222;

        // Insert key with initial value using public put
        ht.put(key, initialValue);
        int initialCount = ht.size();

        // Overwrite value using putInternal
        ht.putInternal(key, newValue);

        // Size should remain the same
        int newCount = ht.size();
        if (newCount != initialCount) {
            throw new RuntimeException("Count changed after putInternal for existing key");
        }

        // Value should be updated
        int retrieved = ht.get(key);
        if (retrieved != newValue) {
            throw new RuntimeException("Value not updated by putInternal for existing key");
        }
    }
}
