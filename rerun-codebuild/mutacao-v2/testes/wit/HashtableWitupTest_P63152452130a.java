/*
 * @test
 * @summary Test java.util.Hashtable.contains(Object) method behavior including NPE on null
 * @run main HashtableWitupTest_P63152452130a
 */

import java.util.Hashtable;

public class HashtableWitupTest_P63152452130a {
    public static void main(String[] args) {
        testContainsWithNullThrowsNPE();
        testContainsWithPresentValueReturnsTrue();
        testContainsWithAbsentValueReturnsFalse();
    }

    private static void testContainsWithNullThrowsNPE() {
        Hashtable<String, String> ht = new Hashtable<>();
        boolean npeThrown = false;
        try {
            ht.contains(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when calling contains(null)");
        }
    }

    private static void testContainsWithPresentValueReturnsTrue() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key1", "value1");
        ht.put("key2", "value2");
        if (!ht.contains("value1")) {
            throw new AssertionError("contains should return true for existing value 'value1'");
        }
        if (!ht.contains("value2")) {
            throw new AssertionError("contains should return true for existing value 'value2'");
        }
    }

    private static void testContainsWithAbsentValueReturnsFalse() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key1", "value1");
        if (ht.contains("nonexistent")) {
            throw new AssertionError("contains should return false for absent value 'nonexistent'");
        }
    }
}
