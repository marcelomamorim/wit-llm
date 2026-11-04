/*
 * @test
 * @summary Test java.util.Properties.contains(Object) method behavior including null handling
 * @run main java.util.PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties props = new Properties();

        // Test contains with a non-null value not present
        boolean result1 = props.contains("nonexistent");
        if (result1) {
            throw new RuntimeException("Test failed: contains returned true for absent value");
        }

        // Test contains with a value that is present
        props.put("key1", "value1");
        boolean result2 = props.contains("value1");
        if (!result2) {
            throw new RuntimeException("Test failed: contains returned false for present value");
        }

        // Test contains with a different value
        boolean result3 = props.contains("value2");
        if (result3) {
            throw new RuntimeException("Test failed: contains returned true for absent value");
        }

        // Test contains with null value - should throw NullPointerException
        boolean npeThrown = false;
        try {
            props.contains(null);
        } catch (NullPointerException npe) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Test failed: contains did not throw NullPointerException for null value");
        }
    }
}
