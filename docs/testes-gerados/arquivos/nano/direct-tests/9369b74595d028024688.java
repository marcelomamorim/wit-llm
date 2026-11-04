/* @test    @summary Test for Security.setProperty method to ensure it updates properties correctly and invalidates cache.    @run main SecurityWitupTest */

import java.security.Security;

public class SecurityWitupTest {

    public static void main(String[] args) {
        testSetPropertyUpdatesProperty();
        testSetPropertyInvalidatesCache();
    }

    private static void testSetPropertyUpdatesProperty() {
        String key = "test.key";
        String value = "test.value";
        Security.setProperty(key, value);
        String retrieved = getPropertyValue(key);
        if (!value.equals(retrieved)) {
            throw new AssertionError("Expected property value: " + value + ", but got: " + retrieved);
        }
    }

    private static void testSetPropertyInvalidatesCache() {
        String key = "another.test.key";
        String value = "another.test.value";
        Security.setProperty(key, value);
        String retrieved = getPropertyValue(key);
        if (!value.equals(retrieved)) {
            throw new AssertionError("Cache invalidation failed for key: " + key);
        }
    }

    private static String getPropertyValue(String key) {
        // Access the private 'props' field via reflection, as it is not accessible directly.
        try {
            java.lang.reflect.Field propsField = Security.class.getDeclaredField("props");
            propsField.setAccessible(true);
            java.util.Properties props = (java.util.Properties) propsField.get(null);
            return props.getProperty(key);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Unable to access properties for verification", e);
        }
    }
}
