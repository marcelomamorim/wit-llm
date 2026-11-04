/* @test    @summary Test for Provider.putIfAbsent method behavior    @run main ProviderWitupTest */

import java.security.Provider;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testPutIfAbsentAddsNewProperty();
        testPutIfAbsentReplacesExistingProperty();
    }

    static void testPutIfAbsentAddsNewProperty() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Provider") {};
        Object result = provider.putIfAbsent("key1", "value1");
        // Expect null since key1 was not present before
        if (result != null) {
            throw new AssertionError("Expected null when adding new property, but got: " + result);
        }
        Object value = provider.get("key1");
        if (!"value1".equals(value)) {
            throw new AssertionError("Property key1 should be 'value1', but was: " + value);
        }
    }

    static void testPutIfAbsentReplacesExistingProperty() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Provider") {};
        provider.put("key2", "initialValue");
        Object previous = provider.putIfAbsent("key2", "newValue");
        // Expect previous value, which is 'initialValue'
        if (!"initialValue".equals(previous)) {
            throw new AssertionError("Expected previous value 'initialValue', but got: " + previous);
        }
        Object currentValue = provider.get("key2");
        // Value should remain 'initialValue' after putIfAbsent
        if (!"initialValue".equals(currentValue)) {
            throw new AssertionError("Property key2 should remain 'initialValue', but was: " + currentValue);
        }
    }
}
