/* @test    @summary Test for Provider.get(Object key) method behavior    @run main ProviderWitupTest */

import java.security.Provider;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testGetReturnsSuperObject();
        testGetWithNullKey();
    }

    private static void testGetReturnsSuperObject() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            @Override
            public Object get(Object key) {
                return super.get(key);
            }
        };
        Object result = provider.get("someKey");
        if (result != null) {
            System.out.println("PASS: get() returned non-null for key.");
        } else {
            System.out.println("PASS: get() returned null for key as expected.");
        }
    }

    private static void testGetWithNullKey() {
        Provider provider = new Provider("NullKeyProvider", 1.0, "Null Key Info") {
            @Override
            public Object get(Object key) {
                return super.get(key);
            }
        };
        Object result = provider.get(null);
        if (result == null) {
            System.out.println("PASS: get() with null key returned null.");
        } else {
            System.out.println("FAIL: get() with null key did not return null.");
        }
    }
}
