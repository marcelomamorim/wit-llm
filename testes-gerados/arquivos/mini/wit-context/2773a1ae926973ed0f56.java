/*
 * @test
 * @summary Test sun.security.action.GetPropertyAction.run() for expected behavior and exception paths
 * @run main GetPropertyActionWitupTest
 */

import sun.security.action.GetPropertyAction;

public class GetPropertyActionWitupTest {
    public static void main(String[] args) {
        testRunReturnsSystemProperty();
        testRunReturnsDefaultValueWhenPropertyNotSet();
        testRunThrowsNPEWhenPropertyNameIsNull();
        testRunThrowsIAEWhenPropertyNameIsEmpty();
    }

    private static void testRunReturnsSystemProperty() {
        String key = "java.version";
        String expected = System.getProperty(key);
        GetPropertyAction action = new GetPropertyAction(key);
        String result = action.run();
        if (expected == null) {
            if (result != null) {
                throw new RuntimeException("Expected null but got: " + result);
            }
        } else {
            if (!expected.equals(result)) {
                throw new RuntimeException("Expected '" + expected + "' but got: '" + result + "'");
            }
        }
    }

    private static void testRunReturnsDefaultValueWhenPropertyNotSet() {
        String key = "property.that.does.not.exist";
        String defaultVal = "defaultValue";
        // Ensure property is not set
        System.clearProperty(key);
        GetPropertyAction action = new GetPropertyAction(key, defaultVal);
        String result = action.run();
        if (!defaultVal.equals(result)) {
            throw new RuntimeException("Expected default value '" + defaultVal + "' but got: '" + result + "'");
        }
    }

    private static void testRunThrowsNPEWhenPropertyNameIsNull() {
        try {
            GetPropertyAction action = new GetPropertyAction(null);
            action.run();
            throw new RuntimeException("Expected NullPointerException when property name is null");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testRunThrowsIAEWhenPropertyNameIsEmpty() {
        try {
            GetPropertyAction action = new GetPropertyAction("");
            action.run();
            throw new RuntimeException("Expected IllegalArgumentException when property name is empty string");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
