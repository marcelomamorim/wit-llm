/*
 * @test
 * @summary Verify privilegedGetProperty returns system property values, or null when the property is absent, without a security manager
 * @modules java.base/sun.security.action
 * @run main GetPropertyActionWitupTest
 */

import sun.security.action.GetPropertyAction;

public class GetPropertyActionWitupTest {

    public static void main(String[] args) {
        String key = "witup.getpropertyaction.test.key";
        System.clearProperty(key);

        check(GetPropertyAction.privilegedGetProperty(key) == null,
              "expected null for unset property");

        System.setProperty(key, "value1");
        try {
            check("value1".equals(GetPropertyAction.privilegedGetProperty(key)),
                  "privilegedGetProperty should return the set value");
            check("value1".equals(new GetPropertyAction(key).run()),
                  "run() should return the set value");
            check("value1".equals(new GetPropertyAction(key, "def").run()),
                  "default value must not override an existing property");

            System.setProperty(key, "");
            check("".equals(GetPropertyAction.privilegedGetProperty(key)),
                  "empty string property should be returned as empty string");
        } finally {
            System.clearProperty(key);
        }

        String missing = key + ".missing";
        check("def".equals(new GetPropertyAction(missing, "def").run()),
              "run() should return default for missing property");
        check(new GetPropertyAction(missing).run() == null,
              "run() without default should return null for missing property");
        check(GetPropertyAction.privilegedGetProperty(missing) == null,
              "privilegedGetProperty should return null for missing property");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
