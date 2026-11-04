/* @test    @summary Test getProperty method of java.security.Provider    @run main ProviderWitupTest */

import java.security.Provider;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testGetPropertyReturnsNullForUnknownKey();
        testGetPropertyReturnsValueForKnownKey();
    }

    private static void testGetPropertyReturnsNullForUnknownKey() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Provider") {
            @Override
            public String getProperty(String key) {
                return super.getProperty(key);
            }
        };
        String result = provider.getProperty("unknownKey");
        if (result != null) {
            throw new AssertionError("Expected null for unknown key, but got: " + result);
        }
    }

    private static void testGetPropertyReturnsValueForKnownKey() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Provider") {
            @Override
            public String getProperty(String key) {
                if ("testKey".equals(key)) {
                    return "testValue";
                }
                return super.getProperty(key);
            }
        };
        String value = provider.getProperty("testKey");
        if (!"testValue".equals(value)) {
            throw new AssertionError("Expected 'testValue' for 'testKey', but got: " + value);
        }
    }
}
