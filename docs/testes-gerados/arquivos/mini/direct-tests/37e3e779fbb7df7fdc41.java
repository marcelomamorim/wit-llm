/*
 * @test
 * @summary Test java.security.Provider.getProperty(String) method
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testGetPropertyReturnsNullForUnknownKey();
        testGetPropertyReturnsValueForKnownKey();
        testGetPropertyWithNullKey();
    }

    static void testGetPropertyReturnsNullForUnknownKey() {
        Provider p = new Provider("TestProvider", 1.0, "Test Provider") {
            // No properties set
        };
        String result = p.getProperty("nonexistentKey");
        if (result != null) {
            throw new AssertionError("Expected null for unknown key, got: " + result);
        }
    }

    static void testGetPropertyReturnsValueForKnownKey() {
        Provider p = new Provider("TestProvider", 1.0, "Test Provider") {
            {
                put("knownKey", "knownValue");
            }
        };
        String result = p.getProperty("knownKey");
        if (!"knownValue".equals(result)) {
            throw new AssertionError("Expected 'knownValue' for key 'knownKey', got: " + result);
        }
    }

    static void testGetPropertyWithNullKey() {
        Provider p = new Provider("TestProvider", 1.0, "Test Provider") {};
        try {
            p.getProperty(null);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
