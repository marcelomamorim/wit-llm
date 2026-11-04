/* @test    @summary Test SSLContext.setDefault() method for null and permission checks    @run main SSLContextWitupTest */

import javax.net.ssl.SSLContext;
import java.security.SSLPermission;
import java.security.Permission;

public class SSLContextWitupTest {

    public static void main(String[] args) {
        testSetDefaultWithNull();
        testSetDefaultWithPermission();
    }

    private static void testSetDefaultWithNull() {
        try {
            SSLContext.setDefault(null);
            System.out.println("FAIL: Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testSetDefaultWithPermission() {
        // Save original default to restore later
        SSLContext originalDefault = null;
        try {
            originalDefault = SSLContext.getDefault();
        } catch (Exception e) {
            System.out.println("FAIL: Unable to get default SSLContext: " + e);
            return;
        }
        // Create a dummy SSLContext for testing
        SSLContext dummyContext = null;
        try {
            dummyContext = SSLContext.getInstance("TLS");
        } catch (Exception e) {
            System.out.println("FAIL: Unable to create SSLContext instance: " + e);
            return;
        }
        // Remove security manager for this test
        SecurityManager sm = System.getSecurityManager();
        try {
            System.setSecurityManager(null);
            SSLContext.setDefault(dummyContext);
            System.out.println("PASS: setDefault() succeeded without SecurityManager");
        } catch (Throwable t) {
            System.out.println("FAIL: setDefault() threw exception without SecurityManager: " + t);
        } finally {
            // Restore original security manager
            System.setSecurityManager(sm);
            // Restore original default context
            try {
                SSLContext.setDefault(originalDefault);
            } catch (Exception e) {
                System.out.println("WARNING: Unable to restore original default SSLContext: " + e);
            }
        }
        // Now test with a SecurityManager that checks permission
        SecurityManager testSm = new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
                if (perm instanceof SSLPermission && "setDefaultSSLContext".equals(perm.getName())) {
                    throw new SecurityException("Permission denied") ;
                }
            }
        };
        System.setSecurityManager(testSm);
        try {
            SSLContext.setDefault(dummyContext);
            System.out.println("FAIL: Expected SecurityException due to permission check");
        } catch (SecurityException e) {
            System.out.println("PASS: SecurityException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        } finally {
            System.setSecurityManager(sm);
        }
    }
}
