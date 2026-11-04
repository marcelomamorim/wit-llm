/* @test    @summary Test the putAll method of java.security.Provider    @run main ProviderWitupTest */

import java.security.Provider;
import java.util.HashMap;
import java.util.Map;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testPutAllAddsProperties();
        testPutAllOverridesProperties();
    }

    public static void testPutAllAddsProperties() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {};
        Map<Object, Object> properties = new HashMap<>();
        properties.put("key1", "value1");
        properties.put("key2", "value2");
        provider.putAll(properties);
        assert "value1".equals(provider.get("key1")) : "key1 should be value1";
        assert "value2".equals(provider.get("key2")) : "key2 should be value2";
    }

    public static void testPutAllOverridesProperties() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {};
        provider.put("key1", "initialValue");
        Map<Object, Object> properties = new HashMap<>();
        properties.put("key1", "newValue");
        provider.putAll(properties);
        assert "newValue".equals(provider.get("key1")) : "key1 should be newValue";
    }
}
