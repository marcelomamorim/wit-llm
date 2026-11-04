/* @test    @summary Test the put method of java.security.Provider for setting properties.    @run main ProviderWitupTest */

import java.security.Provider;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testPutMethodSetsProperty();
    }

    public static void testPutMethodSetsProperty() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Provider") {
            @Override
            public synchronized Object put(Object key, Object value) {
                return super.put(key, value);
            }
        };
        Object previous = provider.put("Key1", "Value1");
        Object result = provider.get("Key1");
        if (!"Value1".equals(result)) {
            throw new AssertionError("Expected 'Value1', but got: " + result);
        }
        if (previous != null) {
            throw new AssertionError("Expected previous value to be null, but was: " + previous);
        }
        Object prev = provider.put("Key1", "Value2");
        if (!"Value1".equals(prev)) {
            throw new AssertionError("Expected previous value 'Value1', but got: " + prev);
        }
        Object newVal = provider.get("Key1");
        if (!"Value2".equals(newVal)) {
            throw new AssertionError("Expected 'Value2', but got: " + newVal);
        }
    }
}
