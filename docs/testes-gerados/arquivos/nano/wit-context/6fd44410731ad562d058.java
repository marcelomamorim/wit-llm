/*
 * @test
 * @summary Tests the put method of java.security.Provider for expected behaviors.
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testPutWithNullKeyAndValue();
        testPutWhenNotInitialized();
    }

    private static void testPutWithNullKeyAndValue() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            @Override
            public synchronized Object put(Object key, Object value) {
                // Call the actual method
                return super.put(key, value);
            }
        };
        try {
            Object result = provider.put(null, null);
            // Expecting no exception, result can be null or previous value
            System.out.println("testPutWithNullKeyAndValue passed, result: " + result);
        } catch (Exception e) {
            System.err.println("testPutWithNullKeyAndValue failed: " + e);
        }
    }

    private static void testPutWhenNotInitialized() {
        Provider provider = new Provider("TestProvider2", "1.0", "Test Info") {
            private boolean initialized = false;
            @Override
            public synchronized Object put(Object key, Object value) {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized");
                }
                return super.put(key, value);
            }
        };
        try {
            provider.put("key", "value");
            System.err.println("testPutWhenNotInitialized failed: exception expected but not thrown");
        } catch (IllegalStateException e) {
            System.out.println("testPutWhenNotInitialized passed, caught expected exception: " + e);
        } catch (Exception e) {
            System.err.println("testPutWhenNotInitialized failed: unexpected exception: " + e);
        }
    }
}
