/* @test    @summary Test for GetPropertyAction.privilegedGetProperty handling null inputs.    @run main GetPropertyActionWitupTest */

import sun.security.action.GetPropertyAction;
import java.security.AccessController;

public class GetPropertyActionWitupTest {

    public static void main(String[] args) {
        testPrivilegedGetPropertyWithNullSecurityAndNullProp();
        testPrivilegedGetPropertyWithNullSecurityAndValidProp();
        testPrivilegedGetPropertyWithSecurityManager();
    }

    private static void testPrivilegedGetPropertyWithNullSecurityAndNullProp() {
        String result = null;
        try {
            result = GetPropertyAction.privilegedGetProperty(null);
            // Expecting a NullPointerException or null depending on System.getProperty implementation
            if (result != null) {
                System.out.println("PASS: null property returns: " + result);
            } else {
                System.out.println("PASS: null property returns null as expected.");
            }
        } catch (NullPointerException npe) {
            System.out.println("PASS: NullPointerException thrown as expected.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testPrivilegedGetPropertyWithNullSecurityAndValidProp() {
        String propName = "java.version";
        String result = null;
        try {
            result = GetPropertyAction.privilegedGetProperty(propName);
            if (result != null) {
                System.out.println("PASS: Valid property '" + propName + "' returns: " + result);
            } else {
                System.out.println("FAIL: Valid property '" + propName + "' returned null.");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testPrivilegedGetPropertyWithSecurityManager() {
        // Simulate presence of a security manager by temporarily setting one
        SecurityManager originalSecurityManager = System.getSecurityManager();
        SecurityManager fakeSecurityManager = new SecurityManager() {
            @Override
            public void checkPermission(java.security.Permission perm) {
                // Allow all permissions
            }
        };
        System.setSecurityManager(fakeSecurityManager);
        try {
            String propName = "java.vendor";
            String result = GetPropertyAction.privilegedGetProperty(propName);
            if (result != null) {
                System.out.println("PASS: With security manager, property '" + propName + "' returns: " + result);
            } else {
                System.out.println("FAIL: With security manager, property '" + propName + "' returned null.");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception under security manager: " + e);
        } finally {
            System.setSecurityManager(originalSecurityManager);
        }
    }
}
