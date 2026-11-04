/*
 * @test
 * @summary Test java.lang.System.setProperty behavior
 * @run main SystemWitupTest
 */
public class SystemWitupTest {
    public static void main(String[] args) throws Exception {
        // Basic test: set a property and verify the return value
        String key = "test.key";
        String value1 = "value1";
        String value2 = "value2";

        // Clear property if set
        System.clearProperty(key);

        // Initially, setProperty returns null (no previous value)
        String prev = System.setProperty(key, value1);
        if (prev != null) {
            throw new RuntimeException("Expected null previous value but got: " + prev);
        }

        // Setting again returns previous value
        prev = System.setProperty(key, value2);
        if (!value1.equals(prev)) {
            throw new RuntimeException("Expected previous value '" + value1 + "' but got: " + prev);
        }

        // Verify property is updated
        String current = System.getProperty(key);
        if (!value2.equals(current)) {
            throw new RuntimeException("Expected current value '" + value2 + "' but got: " + current);
        }

        // Test that null key throws NullPointerException
        try {
            System.setProperty(null, "someValue");
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test that null value is accepted (allowed by Properties.setProperty)
        String nullValueKey = "test.nullValueKey";
        System.clearProperty(nullValueKey);
        prev = System.setProperty(nullValueKey, null);
        // Previous value may be null or a string, no exception expected
        String val = System.getProperty(nullValueKey);
        if (val != null) {
            throw new RuntimeException("Expected null value for key '" + nullValueKey + "' but got: " + val);
        }
    }
}
