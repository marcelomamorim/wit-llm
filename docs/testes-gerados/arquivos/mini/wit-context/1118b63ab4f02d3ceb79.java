/*
 * @test
 * @summary Test java.util.Hashtable.Entry.setValue(V) method for NullPointerException and normal behavior
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testSetValueRejectsNull();
        testSetValueReturnsOldValueAndUpdates();
        System.out.println("All tests passed.");
    }

    private static void testSetValueRejectsNull() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key", "initial");
        Hashtable.Entry<String, String> entry = getEntry(table, "key");
        boolean npeThrown = false;
        try {
            entry.setValue(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when setting null value");
        }
    }

    private static void testSetValueReturnsOldValueAndUpdates() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key", "oldValue");
        Hashtable.Entry<String, String> entry = getEntry(table, "key");
        String old = entry.setValue("newValue");
        if (!"oldValue".equals(old)) {
            throw new AssertionError("setValue should return old value");
        }
        String current = table.get("key");
        if (!"newValue".equals(current)) {
            throw new AssertionError("Value should be updated to newValue");
        }
    }

    // Helper method to get the Entry object for a given key
    private static <K, V> Hashtable.Entry<K, V> getEntry(Hashtable<K, V> table, K key) {
        // Hashtable.Entry is a package-private inner class, so we cannot access it directly.
        // Instead, we use the public method 'table.entrySet()' and find the matching entry.
        for (java.util.Map.Entry<K, V> e : table.entrySet()) {
            if (e.getKey().equals(key)) {
                // The Map.Entry returned by Hashtable.entrySet() is actually Hashtable.Entry
                // so we can cast safely.
                @SuppressWarnings("unchecked")
                Hashtable.Entry<K, V> entry = (Hashtable.Entry<K, V>) e;
                return entry;
            }
        }
        throw new AssertionError("Entry for key not found");
    }
}
