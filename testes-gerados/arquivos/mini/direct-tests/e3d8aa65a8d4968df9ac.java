/*
 * @test
 * @summary Test java.security.Provider.getOrDefault method behavior
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a minimal subclass of Provider to test getOrDefault
        Provider p = new Provider("TestProvider", 1.0, "Test Provider") {
            private static final long serialVersionUID = 1L;
        };

        // Test getOrDefault with a key not present returns defaultValue
        Object defaultValue = "default";
        Object result = p.getOrDefault("nonexistentKey", defaultValue);
        if (result != defaultValue) {
            throw new RuntimeException("getOrDefault did not return defaultValue for missing key");
        }

        // Test getOrDefault with a key present returns the value
        p.put("key1", "value1");
        Object val = p.getOrDefault("key1", defaultValue);
        if (!"value1".equals(val)) {
            throw new RuntimeException("getOrDefault did not return the stored value for existing key");
        }

        // Test getOrDefault with null key returns defaultValue (Hashtable allows null keys? No, Hashtable does not allow null keys)
        // So test with null key should throw NullPointerException
        boolean npeThrown = false;
        try {
            p.getOrDefault(null, defaultValue);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("getOrDefault did not throw NullPointerException for null key");
        }

        // Test getOrDefault with null defaultValue returns null if key not present
        Object nullDefault = null;
        Object resNullDefault = p.getOrDefault("absentKey", nullDefault);
        if (resNullDefault != null) {
            throw new RuntimeException("getOrDefault did not return null when defaultValue is null and key absent");
        }

        System.out.println("All tests passed.");
    }
}
