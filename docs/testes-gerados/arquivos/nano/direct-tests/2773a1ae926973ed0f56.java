/* @test    @summary Test GetPropertyAction.run() method for various property values    @run main GetPropertyActionWitupTest */

import sun.security.action.GetPropertyAction;
import java.security.PrivilegedAction;

public class GetPropertyActionWitupTest {

    public static void main(String[] args) {
        testPropertyExists();
        testPropertyDoesNotExist();
        testPropertyWithDefault();
        testPropertyNullDefault();
    }

    private static void testPropertyExists() {
        String propName = "java.version";
        String expected = System.getProperty(propName);
        GetPropertyAction action = new GetPropertyAction(propName);
        String result = action.run();
        if (expected == null) {
            System.out.println("[SKIP] Property 'java.version' not set.");
        } else {
            assert result.equals(expected) : "Expected: " + expected + ", but got: " + result;
        }
    }

    private static void testPropertyDoesNotExist() {
        String propName = "non.existent.property";
        String expected = null;
        GetPropertyAction action = new GetPropertyAction(propName);
        String result = action.run();
        assert result == null : "Expected null, but got: " + result;
    }

    private static void testPropertyWithDefault() {
        String propName = "non.existent.property.with.default";
        String defaultVal = "defaultValue";
        GetPropertyAction action = new GetPropertyAction(propName, defaultVal);
        String result = action.run();
        assert result.equals(defaultVal) : "Expected default: " + defaultVal + ", but got: " + result;
    }

    private static void testPropertyNullDefault() {
        String propName = "another.non.existent.property";
        String defaultVal = null;
        GetPropertyAction action = new GetPropertyAction(propName, defaultVal);
        String result = action.run();
        assert result == null : "Expected null default, but got: " + result;
    }
}
