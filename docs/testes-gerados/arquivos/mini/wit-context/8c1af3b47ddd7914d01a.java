/*
 * @test
 * @summary Test SecurityManager.checkAccess(ThreadGroup) for null argument and permission check
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.lang.ThreadGroup;
import java.security.SecurityException;
import sun.security.util.SecurityConstants;

public class SecurityManagerWitupTest {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();

        // Test that passing null throws NullPointerException
        try {
            sm.checkAccess(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            if (!"thread group can't be null".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch: " + npe.getMessage());
            }
        }

        // Test that passing a non-root ThreadGroup does not throw
        ThreadGroup tg = new ThreadGroup("testGroup");
        sm.checkAccess(tg); // should just return without exception

        // Test that passing the root ThreadGroup triggers permission check
        // We create a SecurityManager subclass to override checkPermission to detect call
        final boolean[] permissionChecked = new boolean[1];
        SecurityManager smWithOverride = new SecurityManager() {
            @Override
            public void checkPermission(java.security.Permission perm) {
                if (perm.equals(SecurityConstants.MODIFY_THREADGROUP_PERMISSION)) {
                    permissionChecked[0] = true;
                } else {
                    super.checkPermission(perm);
                }
            }
        };

        ThreadGroup rootGroup = getRootThreadGroup();
        smWithOverride.checkAccess(rootGroup);
        if (!permissionChecked[0]) {
            throw new RuntimeException("Expected permission check was not performed for root ThreadGroup");
        }
    }

    private static ThreadGroup getRootThreadGroup() {
        ThreadGroup tg = Thread.currentThread().getThreadGroup();
        ThreadGroup parent;
        while ((parent = tg.getParent()) != null) {
            tg = parent;
        }
        return tg;
    }
}
