/* @test    @summary Test for Hashtable.contains(Object) method, focusing on null input behavior    @run main HashtableWitupTest_P14e2f49b0181 */

import java.util.Hashtable;

public class HashtableWitupTest_P14e2f49b0181 {

    public static void main(String[] args) {
        testContainsWithNull();
        testContainsWithExistingObject();
        testContainsWithNonExistingObject();
    }

    private static void testContainsWithNull() {
        Hashtable<Object, Object> ht = new Hashtable<>();
        boolean threwException = false;
        try {
            ht.contains(null);
        } catch (NullPointerException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected NullPointerException when calling contains(null)");
        }
    }

    private static void testContainsWithExistingObject() {
        Hashtable<String, String> ht = new Hashtable<>();
        String key = "key";
        String value = "value";
        ht.put(key, value);
        boolean result = ht.contains(value);
        if (!result) {
            throw new AssertionError("contains() should return true for existing value");
        }
    }

    private static void testContainsWithNonExistingObject() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("a", "b");
        boolean result = ht.contains("nonexistent");
        if (result) {
            throw new AssertionError("contains() should return false for non-existing value");
        }
    }
}
