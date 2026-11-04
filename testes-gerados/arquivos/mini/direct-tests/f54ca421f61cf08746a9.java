/*
 * @test
 * @summary Test java.security.Provider.getAttribute(String) method
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a minimal subclass of Provider to test getAttribute
        Provider p = new Provider("TestProvider", 1.0, "Test Provider") {
            private static final long serialVersionUID = 1L;
            {
                // Add an attribute with key "key1" and value "value1"
                put("key1", "value1");
            }
            // Override getAttribute to use the attributes map with UString keys
            // but since UString is package-private, we rely on put/get behavior
            // The tested method uses attributes.get(new UString(name)) internally
        };

        // Test 1: getAttribute with existing key returns correct value
        String val = p.getAttribute("key1");
        if (!"value1".equals(val)) {
            throw new RuntimeException("Expected value1 but got " + val);
        }

        // Test 2: getAttribute with non-existing key returns null
        String val2 = p.getAttribute("nonexistent");
        if (val2 != null) {
            throw new RuntimeException("Expected null for nonexistent key but got " + val2);
        }

        // Test 3: getAttribute with null argument throws NullPointerException
        boolean npeThrown = false;
        try {
            p.getAttribute(null);
        } catch (NullPointerException npe) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null argument");
        }
    }
}
