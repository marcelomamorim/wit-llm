/*
 * @test
 * @summary Verify System.setProperties replaces the system properties object and that null resets to a fresh, populated Properties instance
 * @run main SystemWitupTest
 */

import java.util.Properties;

public class SystemWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Properties original = System.getProperties();
        try {
            Properties custom = new Properties();
            custom.setProperty("witup.test.key", "witup.test.value");
            System.setProperties(custom);

            check(System.getProperties() == custom, "setProperties(props) must install the same Properties instance");
            check("witup.test.value".equals(System.getProperty("witup.test.key")),
                  "custom property must be visible through System.getProperty");

            System.setProperties(null);

            Properties reset = System.getProperties();
            check(reset != null, "setProperties(null) must not leave a null system properties object");
            check(reset != custom, "setProperties(null) must install a new Properties instance");
            check(reset.getProperty("witup.test.key") == null,
                  "properties from the previous instance must not survive a null reset");
            check(System.getProperty("line.separator") != null,
                  "default system properties must be re-initialized after setProperties(null)");
        } finally {
            System.setProperties(original);
        }
    }
}
