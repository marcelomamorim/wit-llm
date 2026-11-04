/*
 * @test
 * @summary Verifies System.setProperties with a non-null Properties object and with null (reset to defaults)
 * @run main SystemWitupTest
 */
import java.util.Properties;

public class SystemWitupTest {
    public static void main(String[] args) {
        Properties original = System.getProperties();
        try {
            testNonNullIdentity();
            testNullResetsToDefaults();
        } finally {
            System.setProperties(original);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNonNullIdentity() {
        Properties p = new Properties();
        p.setProperty("witup.key", "value1");
        System.setProperties(p);
        check(System.getProperties() == p, "getProperties should return the same instance");
        check("value1".equals(System.getProperty("witup.key")), "custom property should be visible");
        check(System.getProperty("witup.missing") == null, "missing property should be null");
    }

    static void testNullResetsToDefaults() {
        Properties p = new Properties();
        p.setProperty("witup.key", "x");
        System.setProperties(p);
        System.setProperties(null);
        Properties d = System.getProperties();
        check(d != null, "properties should not be null after reset");
        check(d != p, "properties should be replaced after null reset");
        check(d.getProperty("witup.key") == null, "custom property should be gone after reset");
        check(d.getProperty("line.separator") != null, "default line.separator should be present");
        check(d.getProperty("java.version") != null, "default java.version should be present");
    }
}
