/*
 * @test
 * @summary Test sun.security.action.GetPropertyAction.privilegedGetProperty behavior
 * @run main GetPropertyActionWitupTest
 */

import sun.security.action.GetPropertyAction;
import java.security.AccessController;

public class GetPropertyActionWitupTest {
    public static void main(String[] args) {
        testNullPropertyName();
        testExistingProperty();
        testNonExistingProperty();
    }

    private static void testNullPropertyName() {
        // Setting a system property with null key is not allowed and throws NullPointerException
        // privilegedGetProperty(null) should throw NullPointerException as System.getProperty(null) does
        try {
            GetPropertyAction.privilegedGetProperty(null);
            throw new RuntimeException("Expected NullPointerException for null property name");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testExistingProperty() {
        String key = "java.version";
        String expected = System.getProperty(key);
        String actual = GetPropertyAction.privilegedGetProperty(key);
        if (actual == null || !actual.equals(expected)) {
            throw new RuntimeException("Expected property value '" + expected + "' but got '" + actual + "'");
        }
    }

    private static void testNonExistingProperty() {
        String key = "this.property.should.not.exist.12345";
        String actual = GetPropertyAction.privilegedGetProperty(key);
        if (actual != null) {
            throw new RuntimeException("Expected null for non-existing property but got '" + actual + "'");
        }
    }
}
