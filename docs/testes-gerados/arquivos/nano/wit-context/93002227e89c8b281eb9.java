/* @test    @summary Test for sun.security.provider.PolicyFile.run() method handling system property exceptions    @run main PolicyFileWitupTest */

import java.io.File;
import java.net.URL;
import java.net.URI;
import java.security.Security;

public class PolicyFileWitupTest {

    public static void main(String[] args) {
        testNullProperty();
        testEmptyProperty();
    }

    public static void testNullProperty() {
        String originalProp = System.getProperty("test.policy.property");
        try {
            System.clearProperty("test.policy.property");
            sun.security.provider.PolicyFile policyFile = new sun.security.provider.PolicyFile();
            // Simulate allowSystemProperties being true
            boolean allowSystemProperties = true;
            // Use reflection to set allowSystemProperties if needed, or assume true for test
            // Since run() uses allowSystemProperties, we can simulate by setting the property
            // But in current code, allowSystemProperties is not a field; assume code is as is
            // So, we invoke run() and expect it to handle null property gracefully
            java.lang.reflect.Method runMethod = sun.security.provider.PolicyFile.class.getDeclaredMethod("run");
            runMethod.setAccessible(true);
            Object result = runMethod.invoke(policyFile);
            System.out.println("testNullProperty: run() executed with null property, result: " + result);
        } catch (Exception e) {
            System.out.println("testNullProperty: Exception occurred: " + e);
        } finally {
            if (originalProp != null) {
                System.setProperty("test.policy.property", originalProp);
            } else {
                System.clearProperty("test.policy.property");
            }
        }
    }

    public static void testEmptyProperty() {
        String originalProp = System.getProperty("test.policy.property");
        try {
            System.setProperty("test.policy.property", "");
            sun.security.provider.PolicyFile policyFile = new sun.security.provider.PolicyFile();
            java.lang.reflect.Method runMethod = sun.security.provider.PolicyFile.class.getDeclaredMethod("run");
            runMethod.setAccessible(true);
            Object result = runMethod.invoke(policyFile);
            System.out.println("testEmptyProperty: run() executed with empty property, result: " + result);
        } catch (Exception e) {
            System.out.println("testEmptyProperty: Exception occurred: " + e);
        } finally {
            if (originalProp != null) {
                System.setProperty("test.policy.property", originalProp);
            } else {
                System.clearProperty("test.policy.property");
            }
        }
    }
}
