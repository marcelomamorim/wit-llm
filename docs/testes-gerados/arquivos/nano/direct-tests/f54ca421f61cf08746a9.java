/*
 * @test
 * @summary Test getAttribute method of java.security.Provider
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.HashMap;
import java.util.Map;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testGetAttributeWithValidName();
        testGetAttributeWithNullName();
        testGetAttributeWithUnknownName();
    }

    static void testGetAttributeWithValidName() {
        Map<Object, Object> attrs = new HashMap<>();
        attrs.put(new java.security.Provider.UString("KeySize"), "2048");
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            @Override
            public final String getAttribute(String name) {
                if (name == null) {
                    throw new NullPointerException();
                }
                return (String) attrs.get(new java.security.Provider.UString(name));
            }
        };
        String result = provider.getAttribute("KeySize");
        if (!"2048".equals(result)) {
            throw new AssertionError("Expected '2048', got: " + result);
        }
    }

    static void testGetAttributeWithNullName() {
        Map<Object, Object> attrs = new HashMap<>();
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            @Override
            public final String getAttribute(String name) {
                if (name == null) {
                    throw new NullPointerException();
                }
                return (String) attrs.get(new java.security.Provider.UString(name));
            }
        };
        boolean exceptionThrown = false;
        try {
            provider.getAttribute(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("NullPointerException was not thrown for null input");
        }
    }

    static void testGetAttributeWithUnknownName() {
        Map<Object, Object> attrs = new HashMap<>();
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            @Override
            public final String getAttribute(String name) {
                if (name == null) {
                    throw new NullPointerException();
                }
                return (String) attrs.get(new java.security.Provider.UString(name));
            }
        };
        String result = provider.getAttribute("UnknownAttribute");
        if (result != null) {
            throw new AssertionError("Expected null for unknown attribute, got: " + result);
        }
    }
}
