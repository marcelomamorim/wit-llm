/*
 * @test
 * @summary Test sun.security.action.GetPropertyAction run() method behavior
 * @run main GetPropertyActionWitupTest
 */

import sun.security.action.GetPropertyAction;
import java.security.AccessController;

public class GetPropertyActionWitupTest {
    public static void main(String[] args) {
        testPropertySet();
        testPropertyNotSetWithDefault();
        testPropertyNotSetWithoutDefault();
    }

    private static void testPropertySet() {
        final String key = "test.property.set";
        final String value = "valueSet";
        System.setProperty(key, value);
        GetPropertyAction action = new GetPropertyAction(key);
        String result = AccessController.doPrivileged(action);
        if (!value.equals(result)) {
            throw new RuntimeException("Expected '" + value + "' but got '" + result + "'");
        }
        System.clearProperty(key);
    }

    private static void testPropertyNotSetWithDefault() {
        final String key = "test.property.notset.withdefault";
        final String defaultVal = "defaultValue";
        System.clearProperty(key);
        GetPropertyAction action = new GetPropertyAction(key, defaultVal);
        String result = AccessController.doPrivileged(action);
        if (!defaultVal.equals(result)) {
            throw new RuntimeException("Expected default value '" + defaultVal + "' but got '" + result + "'");
        }
    }

    private static void testPropertyNotSetWithoutDefault() {
        final String key = "test.property.notset.withoutdefault";
        System.clearProperty(key);
        GetPropertyAction action = new GetPropertyAction(key);
        String result = AccessController.doPrivileged(action);
        if (result != null) {
            throw new RuntimeException("Expected null but got '" + result + "'");
        }
    }
}
