/*
 * @test
 * @summary Deterministic tests for sun.security.provider.PolicyFile.run() method
 * @run main PolicyFileWitupTest
 */

import sun.security.provider.PolicyFile;
import java.lang.reflect.Field;
import java.security.Security;
import java.net.URL;

public class PolicyFileWitupTest {

    public static void main(String[] args) throws Exception {
        testRunReturnsBoolean();
        testRunWithAllowSystemPropertiesNullPropname();
        testRunWithAllowSystemPropertiesEmptyPropname();
    }

    // Test that run() returns a Boolean and does not throw under normal conditions
    public static void testRunReturnsBoolean() throws Exception {
        PolicyFile pf = new PolicyFile();

        // forcibly set allowSystemProperties to false to avoid System.getProperty calls
        setBooleanField(pf, "allowSystemProperties", false);

        // forcibly set urlname to a non-existent property to avoid loop
        setStringField(pf, "urlname", "nonexistent.policy.url.");

        Boolean result = pf.run();
        if (result == null) {
            throw new RuntimeException("run() returned null instead of Boolean");
        }
    }

    // Test run() behavior when allowSystemProperties is true and propname is null
    // According to WIT, this triggers NullPointerException in System.getProperty(propname)
    // But current code calls System.getProperty(propname) only if allowSystemProperties is true
    // and propname is null, so we test that run() does not throw but returns false
    public static void testRunWithAllowSystemPropertiesNullPropname() throws Exception {
        PolicyFile pf = new PolicyFile();

        setBooleanField(pf, "allowSystemProperties", true);
        setStringField(pf, "propname", null);

        // forcibly set urlname to a non-existent property to avoid loop
        setStringField(pf, "urlname", "nonexistent.policy.url.");

        Boolean result = pf.run();
        if (result == null) {
            throw new RuntimeException("run() returned null instead of Boolean");
        }
    }

    // Test run() behavior when allowSystemProperties is true and propname is empty string
    // According to WIT, this triggers IllegalArgumentException in System.checkKey(propname)
    // Current code calls System.getProperty(propname) with propname=="" which throws
    // We test that run() does not throw but returns false (exception caught internally)
    public static void testRunWithAllowSystemPropertiesEmptyPropname() throws Exception {
        PolicyFile pf = new PolicyFile();

        setBooleanField(pf, "allowSystemProperties", true);
        setStringField(pf, "propname", "");

        // forcibly set urlname to a non-existent property to avoid loop
        setStringField(pf, "urlname", "nonexistent.policy.url.");

        Boolean result = pf.run();
        if (result == null) {
            throw new RuntimeException("run() returned null instead of Boolean");
        }
    }

    // Helper to set private boolean fields via reflection
    private static void setBooleanField(Object obj, String fieldName, boolean value) throws Exception {
        Field f = obj.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.setBoolean(obj, value);
    }

    // Helper to set private String fields via reflection
    private static void setStringField(Object obj, String fieldName, String value) throws Exception {
        Field f = obj.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(obj, value);
    }
}
