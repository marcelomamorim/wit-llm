/*
 * @test
 * @summary Test java.util.Hashtable.containsValue(Object) method
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testContainsValueWithNull();
        testContainsValueWithPresentValue();
        testContainsValueWithAbsentValue();
    }

    private static void testContainsValueWithNull() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key1", "value1");
        try {
            ht.containsValue(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testContainsValueWithPresentValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key1", "value1");
        ht.put("key2", "value2");
        boolean result = ht.containsValue("value1");
        if (!result) {
            throw new RuntimeException("containsValue should return true for present value");
        }
    }

    private static void testContainsValueWithAbsentValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key1", "value1");
        boolean result = ht.containsValue("absent");
        if (result) {
            throw new RuntimeException("containsValue should return false for absent value");
        }
    }
}
