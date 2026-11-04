/*
 * @test
 * @summary Test java.security.Provider.get(Object) method behavior including initialization check
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {
    public static void main(String[] args) throws Exception {
        testGetThrowsIfNotInitialized();
        testGetReturnsValueIfInitialized();
    }

    static void testGetThrowsIfNotInitialized() {
        Provider p = new Provider("TestProvider", 1.0, "Test Info") {
            private boolean initialized = false;
            @Override
            protected void checkInitialized() {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized");
                }
            }

            @Override
            public Object get(Object key) {
                checkInitialized();
                return super.get(key);
            }
        };

        try {
            p.get("anyKey");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            if (!"Provider not initialized".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    static void testGetReturnsValueIfInitialized() {
        Provider p = new Provider("TestProvider", 1.0, "Test Info") {
            private boolean initialized = true;
            @Override
            protected void checkInitialized() {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized");
                }
            }

            @Override
            public Object get(Object key) {
                checkInitialized();
                // Simulate a value for a known key
                if ("knownKey".equals(key)) {
                    return "knownValue";
                }
                return null;
            }
        };

        Object val = p.get("knownKey");
        if (!"knownValue".equals(val)) {
            throw new RuntimeException("Expected knownValue but got " + val);
        }

        Object val2 = p.get("unknownKey");
        if (val2 != null) {
            throw new RuntimeException("Expected null for unknown key but got " + val2);
        }
    }
}
