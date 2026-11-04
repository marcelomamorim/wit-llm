/*
 * @test
 * @summary Witup generated tests for GetPropertyAction.privilegedGetProperty
 * @modules java.base/sun.security.action
 * @run main GetPropertyActionWitupTest
 */

import sun.security.action.GetPropertyAction;

public class GetPropertyActionWitupTest {

    public static void main(String[] args) {
        String key = "witup.getpropertyaction.test.key";
        System.clearProperty(key);

        if (GetPropertyAction.privilegedGetProperty(key) != null) {
            fail("expected null for unset property");
        }

        System.setProperty(key, "value");
        if (!"value".equals(GetPropertyAction.privilegedGetProperty(key))) {
            fail("expected value of set property");
        }

        System.setProperty(key, "");
        if (!"".equals(GetPropertyAction.privilegedGetProperty(key))) {
            fail("expected empty string for empty property value");
        }

        try {
            GetPropertyAction.privilegedGetProperty(null);
            fail("expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }

        try {
            GetPropertyAction.privilegedGetProperty("");
            fail("expected IllegalArgumentException for empty key");
        } catch (IllegalArgumentException expected) {
        }

        System.clearProperty(key);
    }

    private static void fail(String message) {
        throw new RuntimeException(message);
    }
}
