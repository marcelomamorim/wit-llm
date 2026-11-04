/*
 * @test
 * @summary Test java.util.Hashtable.Entry.setValue method behavior
 * @run main java.util.HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.Map;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testSetValueReturnsOldValue();
        testSetValueRejectsNull();
    }

    private static void testSetValueReturnsOldValue() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key", "old");
        Map.Entry<String, String> entry = table.entrySet().iterator().next();

        String oldValue = entry.setValue("new");

        if (!"old".equals(oldValue)) {
            throw new AssertionError("Expected old value 'old', got: " + oldValue);
        }
        if (!"new".equals(entry.getValue())) {
            throw new AssertionError("Expected new value 'new', got: " + entry.getValue());
        }
    }

    private static void testSetValueRejectsNull() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key", "value");
        Map.Entry<String, String> entry = table.entrySet().iterator().next();

        try {
            entry.setValue(null);
            throw new AssertionError("Expected NullPointerException when setting null value");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
