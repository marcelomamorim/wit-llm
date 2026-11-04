/* @test    @summary Test the remove method of java.security.Provider    @run main ProviderWitupTest */

import java.security.Provider;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testRemoveReturnsExpectedObject();
        testRemoveWithNullKey();
    }

    private static void testRemoveReturnsExpectedObject() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            private Object storedValue;
            @Override
            public synchronized Object implRemove(Object key) {
                if ("testKey".equals(key)) {
                    storedValue = "testValue";
                    return storedValue;
                }
                return null;
            }
        };
        Object result = provider.remove("testKey");
        if (result == null || !"testValue".equals(result)) {
            throw new AssertionError("Expected 'testValue', got: " + result);
        }
    }

    private static void testRemoveWithNullKey() {
        Provider provider = new Provider("NullKeyProvider", "1.0", "Info") {
            @Override
            public synchronized Object implRemove(Object key) {
                return null;
            }
        };
        Object result = provider.remove(null);
        if (result != null) {
            throw new AssertionError("Expected null, got: " + result);
        }
    }
}
