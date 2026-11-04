/* @test    @summary Test for Hashtable.replace method handling null value    @run main HashtableWitupTest */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testReplaceWithNullValue();
    }

    private static void testReplaceWithNullValue() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        boolean exceptionThrown = false;
        try {
            table.replace("key1", null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when replacing with null value");
        }
    }
}
