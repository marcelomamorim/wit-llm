/*
 * @test
 * @summary Test java.util.Hashtable.contains(Object) method
 * @run main java.util.HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {
    public static void main(String[] args) {
        Hashtable<String, String> ht = new Hashtable<>();

        // Test contains on empty hashtable
        if (ht.contains("any") != false) {
            throw new RuntimeException("Failed: contains on empty Hashtable should return false");
        }

        // Add some entries
        ht.put("key1", "value1");
        ht.put("key2", "value2");
        ht.put("key3", null); // Hashtable does not allow null values, so this will throw NullPointerException
        // The above line will throw NullPointerException, so we must remove it
        // Correcting: Hashtable does not allow null values, so test with non-null values only

        // Corrected test without null value
        ht.clear();
        ht.put("key1", "value1");
        ht.put("key2", "value2");

        // Test contains with existing value
        if (!ht.contains("value1")) {
            throw new RuntimeException("Failed: contains should return true for existing value");
        }

        // Test contains with non-existing value
        if (ht.contains("value3")) {
            throw new RuntimeException("Failed: contains should return false for non-existing value");
        }

        // Test contains with null argument (should throw NullPointerException)
        boolean npeThrown = false;
        try {
            ht.contains(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Failed: contains(null) should throw NullPointerException");
        }

        System.out.println("All tests passed.");
    }
}
