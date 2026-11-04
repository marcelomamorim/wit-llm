/*
 * @test
 * @summary Test java.util.Hashtable.containsValue(Object) method
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {
    public static void main(String[] args) {
        Hashtable<String, String> ht = new Hashtable<>();

        // Test containsValue on empty Hashtable
        if (ht.containsValue("any") != false) {
            throw new RuntimeException("Failed: containsValue on empty Hashtable should return false");
        }

        // Add a null value is not allowed in Hashtable, so test with non-null values
        ht.put("key1", "value1");
        ht.put("key2", "value2");

        // Test containsValue with existing value
        if (!ht.containsValue("value1")) {
            throw new RuntimeException("Failed: containsValue should return true for existing value");
        }

        // Test containsValue with non-existing value
        if (ht.containsValue("value3")) {
            throw new RuntimeException("Failed: containsValue should return false for non-existing value");
        }

        // Test containsValue with null argument (Hashtable does not allow null values, so should return false)
        if (ht.containsValue(null)) {
            throw new RuntimeException("Failed: containsValue should return false for null value");
        }
    }
}
