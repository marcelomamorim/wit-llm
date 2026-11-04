/*
 * @test
 * @summary Test for Provider.getProperty method, including exception path when not initialized.
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testGetPropertyWhenInitialized();
        testGetPropertyWhenNotInitialized();
    }

    private static void testGetPropertyWhenInitialized() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            @Override
            public String getProperty(String key) {
                return "value";
            }
        };
        String result = provider.getProperty("anyKey");
        if (result == null || !result.equals("value")) {
            throw new AssertionError("Expected 'value', got: " + result);
        }
    }

    private static void testGetPropertyWhenNotInitialized() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            private boolean initialized = false;
            @Override
            public String getProperty(String key) {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized");
                }
                return "value";
            }
            public void checkInitialized() {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized");
                }
            }
        };
        try {
            provider.getProperty("anyKey");
            throw new AssertionError("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
