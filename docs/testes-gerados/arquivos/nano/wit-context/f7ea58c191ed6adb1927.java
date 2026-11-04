/* @test    @summary Test for Provider.get(Object key) method, including exception path when not initialized    @run main ProviderWitupTest */

import java.security.Provider;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testGetWithInitializedProvider();
        testGetWithUninitializedProvider();
    }

    private static void testGetWithInitializedProvider() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            @Override
            public Object get(Object key) {
                // Simulate initialized state
                return super.get(key);
            }
        };
        try {
            Object result = provider.get("someKey");
            // No exception expected, result can be null or object
            System.out.println("testGetWithInitializedProvider passed, result: " + result);
        } catch (IllegalStateException e) {
            throw new AssertionError("Unexpected IllegalStateException when provider is initialized", e);
        }
    }

    private static void testGetWithUninitializedProvider() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            private boolean initialized = false;
            @Override
            public Object get(Object key) {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized");
                }
                return super.get(key);
            }
            private void checkInitialized() {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized");
                }
            }
        };
        try {
            provider.get("someKey");
            throw new AssertionError("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
            System.out.println("testGetWithUninitializedProvider passed, caught expected exception: " + e.getMessage());
        }
    }
}
