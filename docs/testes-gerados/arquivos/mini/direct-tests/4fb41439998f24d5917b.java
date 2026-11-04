/*
 * @test
 * @summary Test java.lang.System.setProperties(Properties) behavior
 * @run main SystemWitupTest
 */

import java.lang.SecurityManager;
import java.util.Properties;

public class SystemWitupTest {
    public static void main(String[] args) {
        testSetPropertiesWithNull();
        testSetPropertiesWithNonNull();
        testSetPropertiesSecurityCheck();
    }

    private static void testSetPropertiesWithNull() {
        // Save original properties to restore later
        Properties original = System.getProperties();
        try {
            System.setProperties(null);
            Properties p = System.getProperties();
            if (p == null) {
                throw new RuntimeException("Properties should not be null after setProperties(null)");
            }
            // The properties should be a Properties instance (not null)
            if (!(p instanceof Properties)) {
                throw new RuntimeException("Properties is not instance of java.util.Properties");
            }
        } finally {
            System.setProperties(original);
        }
    }

    private static void testSetPropertiesWithNonNull() {
        Properties original = System.getProperties();
        Properties testProps = new Properties();
        testProps.setProperty("test.key", "test.value");
        try {
            System.setProperties(testProps);
            Properties p = System.getProperties();
            if (p == null) {
                throw new RuntimeException("Properties should not be null after setProperties(non-null)");
            }
            if (!"test.value".equals(p.getProperty("test.key"))) {
                throw new RuntimeException("Property value mismatch after setProperties");
            }
        } finally {
            System.setProperties(original);
        }
    }

    private static void testSetPropertiesSecurityCheck() {
        // Install a SecurityManager that records if checkPropertiesAccess is called
        final boolean[] called = {false};
        SecurityManager sm = new SecurityManager() {
            @Override
            public void checkPropertiesAccess() {
                called[0] = true;
            }
            @Override
            public void checkPermission(java.security.Permission perm) {
                // Allow all other permissions
            }
        };

        SecurityManager originalSM = System.getSecurityManager();
        try {
            System.setSecurityManager(sm);
            System.setProperties(new Properties());
            if (!called[0]) {
                throw new RuntimeException("SecurityManager.checkPropertiesAccess was not called");
            }
        } finally {
            System.setSecurityManager(originalSM);
        }
    }
}
