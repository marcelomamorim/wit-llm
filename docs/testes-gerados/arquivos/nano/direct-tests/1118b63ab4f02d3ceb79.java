/* @test    @summary Test setValue method of Hashtable for null and non-null values    @run main HashtableWitupTest */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testSetValueWithNonNull();
        testSetValueWithNull();
    }

    private static void testSetValueWithNonNull() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key", "initial");
        String previousValue = table.get("key");
        String newValue = "updated";
        String oldValue = table.setValue(newValue);
        assert oldValue.equals(previousValue) : "Old value should be 'initial'";
        assert table.get("key").equals(newValue) : "Value should be updated to 'updated'";
    }

    private static void testSetValueWithNull() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key", "value");
        try {
            table.setValue(null);
            assert false : "Expected NullPointerException when setting null";
        } catch (NullPointerException e) {
            // expected
        }
        // Ensure original value remains unchanged
        assert table.get("key").equals("value") : "Value should remain 'value'";
    }
}
