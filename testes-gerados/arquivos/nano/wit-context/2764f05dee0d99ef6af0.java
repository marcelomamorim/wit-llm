/*
 * @test
 * @summary Tests for java.util.Hashtable.put method, focusing on null value handling.
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testPutWithNullValue();
        testPutWithValidValues();
    }

    private static void testPutWithNullValue() {
        Hashtable<String, String> table = new Hashtable<>();
        boolean exceptionThrown = false;
        try {
            table.put("key", null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when putting null value");
        }
    }

    private static void testPutWithValidValues() {
        Hashtable<String, String> table = new Hashtable<>();
        String previous = table.put("a", "1");
        if (previous != null) {
            throw new AssertionError("Expected previous value to be null for new key");
        }
        String oldValue = table.put("a", "2");
        if (!"1".equals(oldValue)) {
            throw new AssertionError("Expected old value to be '1'");
        }
        String currentValue = table.get("a");
        if (!"2".equals(currentValue)) {
            throw new AssertionError("Expected current value to be '2'");
        }
    }
}
