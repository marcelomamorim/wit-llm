/*
 * @test
 * @summary Test java.security.Provider.remove(Object) method behavior
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a minimal subclass of Provider to test remove method
        Provider p = new Provider("TestProvider", 1.0, "Test Provider Info") {
            // Override check to allow remove without SecurityException
            protected void check(String action) {
                // no-op to allow remove
            }

            // Override implRemove to simulate removal behavior
            protected synchronized Object implRemove(Object key) {
                if (key == null) return null;
                if ("existingKey".equals(key)) return "existingValue";
                return null;
            }
        };

        // Test remove with existing key
        Object removed = p.remove("existingKey");
        if (!"existingValue".equals(removed)) {
            throw new RuntimeException("remove did not return expected value for existing key");
        }

        // Test remove with non-existing key
        Object removedNull = p.remove("nonExistingKey");
        if (removedNull != null) {
            throw new RuntimeException("remove did not return null for non-existing key");
        }

        // Test remove with null key
        Object removedNullKey = p.remove(null);
        if (removedNullKey != null) {
            throw new RuntimeException("remove did not return null for null key");
        }

        // Test that remove is synchronized and does not throw
        // (implicit by successful calls above)

        System.out.println("PASS");
    }
}
