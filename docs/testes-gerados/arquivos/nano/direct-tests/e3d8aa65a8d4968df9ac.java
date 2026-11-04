/*
 * @test
 * @summary Test for Provider.getOrDefault method to ensure correct default value return behavior.
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testGetOrDefaultWithExistingKey();
        testGetOrDefaultWithNonExistingKey();
        testGetOrDefaultWithNullKey();
        testGetOrDefaultWithNullDefault();
    }

    static void testGetOrDefaultWithExistingKey() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {};
        String key = "name";
        Object value = provider.getOrDefault(key, "default");
        if (!"TestProvider".equals(value)) {
            throw new AssertionError("Expected 'TestProvider', but got: " + value);
        }
    }

    static void testGetOrDefaultWithNonExistingKey() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {};
        String key = "nonExistingKey";
        Object defaultValue = "default";
        Object value = provider.getOrDefault(key, defaultValue);
        if (value != defaultValue) {
            throw new AssertionError("Expected default value, but got: " + value);
        }
    }

    static void testGetOrDefaultWithNullKey() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {};
        Object defaultValue = "default";
        Object value = provider.getOrDefault(null, defaultValue);
        if (value != defaultValue) {
            throw new AssertionError("Expected default value for null key, but got: " + value);
        }
    }

    static void testGetOrDefaultWithNullDefault() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {};
        String key = "name";
        Object value = provider.getOrDefault(key, null);
        if (!"TestProvider".equals(value)) {
            throw new AssertionError("Expected 'TestProvider', but got: " + value);
        }
    }
}
