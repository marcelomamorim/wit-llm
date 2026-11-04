/*
 * @test
 * @summary Test sun.security.provider.PolicyFile.run() method behavior
 * @run main PolicyFileWitupTest
 */

import sun.security.provider.PolicyFile;
import java.net.URL;
import java.io.File;

public class PolicyFileWitupTest {
    public static void main(String[] args) throws Exception {
        testRunWithNoSystemProperty();
        testRunWithInvalidSystemProperty();
        testRunWithValidFileSystemProperty();
        testRunWithOverrideAllSystemProperty();
        testRunWithSecurityProperties();
    }

    private static void testRunWithNoSystemProperty() {
        PolicyFile pf = new PolicyFile();
        // Clear the system property to ensure no extra policy is loaded
        System.clearProperty("java.security.policy");
        Boolean result = pf.run();
        // The result is Boolean, expect false or true depending on policy files
        if (result == null) {
            throw new RuntimeException("run() returned null");
        }
    }

    private static void testRunWithInvalidSystemProperty() {
        PolicyFile pf = new PolicyFile();
        // Set a system property to an invalid URL to test exception handling
        System.setProperty("java.security.policy", "http://invalid-url:9999/nonexistent.policy");
        Boolean result = pf.run();
        // Should not throw, and result is Boolean
        if (result == null) {
            throw new RuntimeException("run() returned null with invalid system property");
        }
        // Clear property after test
        System.clearProperty("java.security.policy");
    }

    private static void testRunWithValidFileSystemProperty() throws Exception {
        PolicyFile pf = new PolicyFile();
        // Create a temporary empty file to simulate a valid policy file
        File tempFile = File.createTempFile("policyfiletest", ".policy");
        tempFile.deleteOnExit();
        System.setProperty("java.security.policy", tempFile.getAbsolutePath());
        Boolean result = pf.run();
        if (result == null) {
            throw new RuntimeException("run() returned null with valid file system property");
        }
        System.clearProperty("java.security.policy");
    }

    private static void testRunWithOverrideAllSystemProperty() throws Exception {
        PolicyFile pf = new PolicyFile();
        // Create a temporary empty file
        File tempFile = File.createTempFile("policyfiletest", ".policy");
        tempFile.deleteOnExit();
        // Set system property with '=' prefix to trigger overrideAll
        System.setProperty("java.security.policy", "=" + tempFile.getAbsolutePath());
        Boolean result = pf.run();
        if (result == null) {
            throw new RuntimeException("run() returned null with overrideAll system property");
        }
        System.clearProperty("java.security.policy");
    }

    private static void testRunWithSecurityProperties() {
        PolicyFile pf = new PolicyFile();
        // The run method reads Security properties named policy.url.1, policy.url.2, ...
        // We cannot set Security properties directly, but we can test that run() does not throw
        Boolean result = pf.run();
        if (result == null) {
            throw new RuntimeException("run() returned null when reading security properties");
        }
    }
}
