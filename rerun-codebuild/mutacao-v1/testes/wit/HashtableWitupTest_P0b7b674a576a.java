/* @test    @summary Test for Hashtable.put to verify NullPointerException when value is null    @run main HashtableWitupTest_P0b7b674a576a */

import java.util.Hashtable;

public class HashtableWitupTest_P0b7b674a576a {

    public static void main(String[] args) {
        testPutWithNullValue();
    }

    private static void testPutWithNullValue() {
        Hashtable<String, String> table = new Hashtable<>();
        String key = "testKey";
        String value = null;
        try {
            table.put(key, value);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }
}
