/*
 * @test
 * @summary Test SecurityManager.checkAccept() method for null host and valid host
 * @run main SecurityManagerWitupTest_P242eeea55dce
 */

import java.lang.SecurityManager;
import java.net.SocketPermission;
import java.security.Permission;

public class SecurityManagerWitupTest_P242eeea55dce {

    public static void main(String[] args) {
        testCheckAcceptNullHost();
        testCheckAcceptValidHost();
    }

    static void testCheckAcceptNullHost() {
        SecurityManager sm = new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
                if (perm instanceof SocketPermission) {
                    // Do nothing, permit for test
                } else {
                    super.checkPermission(perm);
                }
            }
        };
        try {
            sm.checkAccept(null, 80);
            throw new AssertionError("Expected NullPointerException for null host");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testCheckAcceptValidHost() {
        SecurityManager sm = new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
                if (perm instanceof SocketPermission) {
                    // Do nothing, permit for test
                } else {
                    super.checkPermission(perm);
                }
            }
        };
        try {
            sm.checkAccept("localhost", 80);
            // No exception expected
        } catch (SecurityException e) {
            throw new AssertionError("Unexpected SecurityException for valid host", e);
        }
    }
}
