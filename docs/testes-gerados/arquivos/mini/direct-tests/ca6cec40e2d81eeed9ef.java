/*
 * @test
 * @summary Test sun.security.action.GetPropertyAction.privilegedGetProperty method
 * @run main GetPropertyActionWitupTest
 */

import sun.security.action.GetPropertyAction;
import java.security.AccessController;

public class GetPropertyActionWitupTest {
    public static void main(String[] args) {
        testPrivilegedGetPropertyReturnsSystemProperty();
        testPrivilegedGetPropertyReturnsNullForUnknownProperty();
    }

    private static void testPrivilegedGetPropertyReturnsSystemProperty() {
        final String key = "java.version";
        String expected = System.getProperty(key);
        String actual = GetPropertyAction.privilegedGetProperty(key);
        if (expected == null) {
            if (actual != null) {
                throw new RuntimeException("Expected null but got '" + actual + "' for property: " + key);
            }
        } else {
            if (!expected.equals(actual)) {
                throw new RuntimeException("Expected '" + expected + "' but got '" + actual + "' for property: " + key);
            }
        }
    }

    private static void testPrivilegedGetPropertyReturnsNullForUnknownProperty() {
        final String key = "this.property.should.not.exist.1234567890";
        String expected = System.getProperty(key);
        String actual = GetPropertyAction.privilegedGetProperty(key);
        if (expected != null) {
            throw new RuntimeException("Test setup failure: property '" + key + "' unexpectedly exists with value '" + expected + "'");
        }
        if (actual != null) {
            throw new RuntimeException("Expected null but got '" + actual + "' for unknown property: " + key);
        }
    }
}
