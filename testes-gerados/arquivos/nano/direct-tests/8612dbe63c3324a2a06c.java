/*
 * @test
 * @summary Test the remove method of java.security.Provider
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testRemoveReturnsTrueWhenPropertyExists();
        testRemoveReturnsFalseWhenPropertyDoesNotExist();
    }

    private static void testRemoveReturnsTrueWhenPropertyExists() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {};
        String key = "testKey";
        String value = "testValue";
        // Simulate adding a property
        provider.put(key, value);
        // Remove the property
        boolean result = provider.remove(key, value);
        if (!result) {
            throw new AssertionError("Expected remove to return true when property exists");
        }
    }

    private static void testRemoveReturnsFalseWhenPropertyDoesNotExist() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {};
        String key = "nonExistentKey";
        String value = "nonExistentValue";
        // Attempt to remove a non-existent property
        boolean result = provider.remove(key, value);
        if (result) {
            throw new AssertionError("Expected remove to return false when property does not exist");
        }
    }
}
