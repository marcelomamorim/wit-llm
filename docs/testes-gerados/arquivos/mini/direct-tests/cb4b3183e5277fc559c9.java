/*
 * @test
 * @summary Test java.security.Provider.putIfAbsent method behavior
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a minimal subclass of Provider to test putIfAbsent
        Provider p = new Provider("TestProvider", 1.0, "Test Provider") {
            private static final long serialVersionUID = 1L;
            protected void check(String action) {
                // Allow all actions for testing
            }
            // implPutIfAbsent is package-private in Provider, so we test only putIfAbsent
        };

        // Test putIfAbsent with new key-value
        Object prev = p.putIfAbsent("key1", "value1");
        if (prev != null) {
            throw new RuntimeException("Expected null on first putIfAbsent");
        }

        // Test putIfAbsent with existing key, should return existing value
        Object prev2 = p.putIfAbsent("key1", "value2");
        if (!"value1".equals(prev2)) {
            throw new RuntimeException("Expected existing value 'value1' on putIfAbsent");
        }

        // Test putIfAbsent with null key and value
        Object prev3 = p.putIfAbsent(null, null);
        if (prev3 != null) {
            throw new RuntimeException("Expected null on putIfAbsent with null key and value");
        }

        // Test putIfAbsent with null key and non-null value
        Object prev4 = p.putIfAbsent(null, "valueNullKey");
        if (prev4 != null) {
            throw new RuntimeException("Expected null on putIfAbsent with null key and non-null value");
        }

        // Test putIfAbsent with non-null key and null value
        Object prev5 = p.putIfAbsent("keyNullValue", null);
        if (prev5 != null) {
            throw new RuntimeException("Expected null on putIfAbsent with non-null key and null value");
        }

        System.out.println("All tests passed.");
    }
}
