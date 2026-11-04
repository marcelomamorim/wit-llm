/*
 * @test
 * @summary Test java.security.Provider.put(Object,Object) method behavior
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a minimal subclass of Provider to test put method
        Provider p = new Provider("TestProvider", 1.0, "Test Provider") {
            protected void check(String action) {
                // Allow all actions for testing
            }
            protected Object implPut(Object key, Object value) {
                // For testing, store key-value in the Provider's properties map
                return super.put(key, value);
            }
        };

        // Test put with normal key and value
        Object ret1 = p.put("key1", "value1");
        if (ret1 != null) {
            throw new RuntimeException("Expected null on first put, got: " + ret1);
        }
        Object val1 = p.get("key1");
        if (!"value1".equals(val1)) {
            throw new RuntimeException("Expected value1 for key1, got: " + val1);
        }

        // Test put with null key (should throw NullPointerException as per Hashtable contract)
        boolean npeThrown = false;
        try {
            p.put(null, "value");
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null key");
        }

        // Test put with null value (allowed by Hashtable, returns previous value or null)
        Object ret2 = p.put("key1", null);
        if (!"value1".equals(ret2)) {
            throw new RuntimeException("Expected previous value1 when putting null value, got: " + ret2);
        }
        Object val2 = p.get("key1");
        if (val2 != null) {
            throw new RuntimeException("Expected null value for key1 after putting null, got: " + val2);
        }

        // Test put with same key and value again
        Object ret3 = p.put("key1", null);
        if (ret3 != null) {
            throw new RuntimeException("Expected null previous value when putting null again, got: " + ret3);
        }

        System.out.println("All tests passed.");
    }
}
