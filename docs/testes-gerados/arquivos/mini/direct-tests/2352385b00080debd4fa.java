/*
 * @test
 * @summary Test java.security.Provider.replace method behavior
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a minimal subclass of Provider to test replace method
        Provider p = new Provider("TestProvider", 1.0, "Test Provider") {
            @Override
            public synchronized Object put(Object key, Object value) {
                return super.put(key, value);
            }
            // Expose implReplace for testing via reflection since it's package-private
            // but we cannot access it directly, so we rely on replace method only
        };

        // Test replace with a new key-value pair
        Object oldValue = p.replace("key1", "value1");
        if (oldValue != null) {
            throw new RuntimeException("Expected null when replacing non-existing key");
        }

        // Test replace with existing key, should return old value
        Object oldValue2 = p.replace("key1", "value2");
        if (!"value1".equals(oldValue2)) {
            throw new RuntimeException("Expected old value 'value1' when replacing existing key");
        }

        // Test replace with null key (should throw NullPointerException as per Hashtable contract)
        boolean npeThrown = false;
        try {
            p.replace(null, "value");
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when key is null");
        }

        // Test replace with null value (allowed in Hashtable, value can be null)
        Object oldValue3 = p.replace("key1", null);
        if (!"value2".equals(oldValue3)) {
            throw new RuntimeException("Expected old value 'value2' when replacing existing key with null value");
        }
        Object newValue = p.get("key1");
        if (newValue != null) {
            throw new RuntimeException("Expected null value after replacing with null");
        }
    }
}
