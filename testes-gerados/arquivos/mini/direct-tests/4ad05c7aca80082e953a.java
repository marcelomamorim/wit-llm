/*
 * @test
 * @summary Test sun.text.IntHashtable.get(int) method behavior
 * @run main IntHashtableWitupTest
 */

public class IntHashtableWitupTest {
    public static void main(String[] args) {
        IntHashtable ht = new IntHashtable();

        // Initially empty, get should return defaultValue (0)
        int defaultValue = ht.getDefaultValue();
        if (ht.get(12345) != defaultValue) {
            throw new RuntimeException("get() on empty table did not return defaultValue");
        }

        // Put a key-value pair and verify get returns the value
        ht.put(10, 100);
        int val = ht.get(10);
        if (val != 100) {
            throw new RuntimeException("get() did not return the value just put");
        }

        // Put another key-value pair and verify get returns correct values
        ht.put(20, 200);
        if (ht.get(20) != 200) {
            throw new RuntimeException("get() did not return the correct value for key 20");
        }
        if (ht.get(10) != 100) {
            throw new RuntimeException("get() value for key 10 changed unexpectedly");
        }

        // Remove a key and verify get returns defaultValue
        ht.remove(10);
        if (ht.get(10) != defaultValue) {
            throw new RuntimeException("get() after remove did not return defaultValue");
        }

        // Verify get for a key never added returns defaultValue
        if (ht.get(9999) != defaultValue) {
            throw new RuntimeException("get() for unknown key did not return defaultValue");
        }

        // Change defaultValue and verify get returns new defaultValue for missing keys
        ht.setDefaultValue(-1);
        if (ht.get(10) != -1) {
            throw new RuntimeException("get() did not return updated defaultValue after setDefaultValue");
        }

        // Put a key with value equal to new defaultValue and verify get returns it
        ht.put(30, -1);
        if (ht.get(30) != -1) {
            throw new RuntimeException("get() did not return the value equal to defaultValue");
        }

        System.out.println("PASS");
    }
}
