/*
 * @test
 * @summary Test for Hashtable.put method, focusing on NullPointerException when value is null
 * @run main HashtableWitupTest_Pb3d90bba447d
 */

import java.util.Hashtable;

public class HashtableWitupTest_Pb3d90bba447d {

    public static void main(String[] args) {
        testPutNullValue();
        testPutValidValue();
    }

    public static void testPutNullValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        boolean exceptionThrown = false;
        try {
            ht.put("key", null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when putting null value");
        }
    }

    public static void testPutValidValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        String prev = ht.put("key", "value");
        if (prev != null) {
            throw new AssertionError("Expected previous value to be null for new key");
        }
        String retrieved = ht.get("key");
        if (!"value".equals(retrieved)) {
            throw new AssertionError("Value retrieved does not match inserted value");
        }
    }
}
