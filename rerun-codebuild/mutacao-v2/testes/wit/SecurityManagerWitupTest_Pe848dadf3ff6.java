/*
 * @test
 * @summary Test SecurityManager.checkLink(String) for null argument and permission check
 * @run main SecurityManagerWitupTest_Pe848dadf3ff6
 */

import java.lang.SecurityManager;
import java.security.AccessControlException;
import java.security.Permission;
import java.security.RuntimePermission;

public class SecurityManagerWitupTest_Pe848dadf3ff6 {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();

        // Test that passing null throws NullPointerException
        try {
            sm.checkLink(null);
            throw new Error("Expected NullPointerException for null argument");
        } catch (NullPointerException npe) {
            if (!"library can't be null".equals(npe.getMessage())) {
                throw new Error("Unexpected NullPointerException message: " + npe.getMessage());
            }
        }

        // Test that passing a non-null string calls checkPermission with correct RuntimePermission
        // Since default SecurityManager.checkPermission calls AccessController.checkPermission,
        // and no security manager is installed that denies permission, this should pass silently.
        String libName = "testLib";
        sm.checkLink(libName);

        // To test permission denial, install a custom SecurityManager that denies the permission
        SecurityManager denyingSM = new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
                if (perm instanceof RuntimePermission && perm.getName().equals("loadLibrary." + libName)) {
                    throw new SecurityException("Permission denied: " + perm.getName());
                }
            }
        };

        try {
            denyingSM.checkLink(libName);
            throw new Error("Expected SecurityException for denied permission");
        } catch (SecurityException se) {
            if (!se.getMessage().equals("Permission denied: loadLibrary." + libName)) {
                throw new Error("Unexpected SecurityException message: " + se.getMessage());
            }
        }
    }
}
