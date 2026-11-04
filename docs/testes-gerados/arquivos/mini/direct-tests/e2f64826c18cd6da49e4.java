/*
 * @test
 * @summary Test java.util.Hashtable.contains(Object) method behavior
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testContainsWithNullValue();
        testContainsWithExistingValue();
        testContainsWithNonExistingValue();
    }

    private static void testContainsWithNullValue() {
        Hashtable<Object, Object> ht = new Hashtable<>();
        try {
            ht.contains(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testContainsWithExistingValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key1", "value1");
        ht.put("key2", "value2");
        boolean result = ht.contains("value1");
        if (!result) {
            throw new RuntimeException("Expected contains to return true for existing value");
        }
    }

    private static void testContainsWithNonExistingValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key1", "value1");
        ht.put("key2", "value2");
        boolean result = ht.contains("value3");
        if (result) {
            throw new RuntimeException("Expected contains to return false for non-existing value");
        }
    }
}
