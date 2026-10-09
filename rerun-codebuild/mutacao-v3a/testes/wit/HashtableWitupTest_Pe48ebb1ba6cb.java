/* @test    @summary Test for java.util.Hashtable.contains(Object) - null argument handling    @run main HashtableWitupTest_Pe48ebb1ba6cb */

import java.util.Hashtable;

public class HashtableWitupTest_Pe48ebb1ba6cb {

    public static void main(String[] args) {
        testContainsWithNull();
        testContainsWithExistingValue();
        testContainsWithNonExistingValue();
    }

    static void testContainsWithNull() {
        Hashtable<Object, Object> ht = new Hashtable<>();
        try {
            ht.contains(null);
            System.err.println("Failed: Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // Test passes
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }

    static void testContainsWithExistingValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key", "value");
        boolean result = ht.contains("value");
        if (!result) {
            System.err.println("Failed: contains should return true for existing value");
        }
    }

    static void testContainsWithNonExistingValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key", "value");
        boolean result = ht.contains("nonexistent");
        if (result) {
            System.err.println("Failed: contains should return false for non-existing value");
        }
    }
}
