/* @test    @summary Test privilegedGetProperty with existing property    @run main GetPropertyActionWitupTest */

import sun.security.action.GetPropertyAction;
import java.security.AccessController;

public class GetPropertyActionWitupTest {

    public static void main(String[] args) {
        testPrivilegedGetPropertyWithExistingProperty();
        testPrivilegedGetPropertyWithNonExistingProperty();
    }

    static void testPrivilegedGetPropertyWithExistingProperty() {
        String propertyName = "java.version";
        String result = GetPropertyAction.privilegedGetProperty(propertyName);
        String systemProperty = System.getProperty(propertyName);
        if (systemProperty == null) {
            System.out.println("Test skipped: 'java.version' property not found.");
            return;
        }
        if (!result.equals(systemProperty)) {
            throw new AssertionError("Expected: " + systemProperty + ", but got: " + result);
        }
    }

    static void testPrivilegedGetPropertyWithNonExistingProperty() {
        String propertyName = "non.existent.property.for.testing";
        String result = GetPropertyAction.privilegedGetProperty(propertyName);
        if (result != null) {
            throw new AssertionError("Expected null for non-existent property, but got: " + result);
        }
    }
}
