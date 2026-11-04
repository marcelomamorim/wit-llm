/*
 * @test
 * @summary Test SecurityManager.checkAccess(ThreadGroup) behavior
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.lang.ThreadGroup;
import java.security.Permission;
import sun.security.util.SecurityConstants;

public class SecurityManagerWitupTest {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();

        // Test 1: Passing null should throw NullPointerException
        try {
            sm.checkAccess(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            if (!"thread group can't be null".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch");
            }
        }

        // Test 2: Passing rootGroup should call checkPermission with MODIFY_THREADGROUP_PERMISSION
        // We cannot override checkPermission easily, so we create a subclass to observe the call
        final Permission expectedPerm = SecurityConstants.MODIFY_THREADGROUP_PERMISSION;
        ThreadGroup rootGroup = getRootThreadGroup();

        TestSecurityManager testSM = new TestSecurityManager(expectedPerm, rootGroup);

        // Should not throw
        testSM.checkAccess(rootGroup);

        if (!testSM.permissionChecked) {
            throw new RuntimeException("checkPermission was not called for rootGroup");
        }

        // Test 3: Passing a non-root ThreadGroup should do nothing (no exception, no permission check)
        ThreadGroup nonRootGroup = new ThreadGroup(rootGroup, "nonRoot");
        testSM.permissionChecked = false;
        testSM.checkAccess(nonRootGroup);
        if (testSM.permissionChecked) {
            throw new RuntimeException("checkPermission should not be called for non-root group");
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

    static class TestSecurityManager extends SecurityManager {
        final Permission expectedPermission;
        final ThreadGroup rootGroup;
        boolean permissionChecked = false;

        TestSecurityManager(Permission expectedPermission, ThreadGroup rootGroup) {
            this.expectedPermission = expectedPermission;
            this.rootGroup = rootGroup;
        }

        @Override
        public void checkPermission(Permission perm) {
            if (perm.equals(expectedPermission)) {
                permissionChecked = true;
            } else {
                throw new SecurityException("Unexpected permission: " + perm);
            }
        }
    }
}
