/*
 * @test
 * @summary Verifies SecurityManager.checkAccess(ThreadGroup) null handling, root group permission check and non-root pass-through
 * @run main SecurityManagerWitupTest
 */

import java.security.Permission;

public class SecurityManagerWitupTest {

    static class RecordingManager extends SecurityManager {
        final boolean deny;
        int count;
        Permission last;

        RecordingManager(boolean deny) {
            this.deny = deny;
        }

        @Override
        public void checkPermission(Permission perm) {
            count++;
            last = perm;
            if (deny) {
                throw new SecurityException("denied");
            }
        }
    }

    static ThreadGroup rootGroup() {
        ThreadGroup g = Thread.currentThread().getThreadGroup();
        while (g.getParent() != null) {
            g = g.getParent();
        }
        return g;
    }

    static void testNullGroup() {
        RecordingManager sm = new RecordingManager(false);
        try {
            sm.checkAccess((ThreadGroup) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
            if (!"thread group can't be null".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
        if (sm.count != 0) {
            throw new RuntimeException("checkPermission called for null group");
        }
    }

    static void testRootGroupAllowed() {
        RecordingManager sm = new RecordingManager(false);
        sm.checkAccess(rootGroup());
        if (sm.count != 1) {
            throw new RuntimeException("expected one checkPermission call, got " + sm.count);
        }
        if (!(sm.last instanceof RuntimePermission)
                || !"modifyThreadGroup".equals(sm.last.getName())) {
            throw new RuntimeException("unexpected permission: " + sm.last);
        }
    }

    static void testRootGroupDenied() {
        RecordingManager sm = new RecordingManager(true);
        try {
            sm.checkAccess(rootGroup());
            throw new RuntimeException("expected SecurityException for root group");
        } catch (SecurityException e) {
            // expected
        }
        if (sm.count != 1) {
            throw new RuntimeException("expected one checkPermission call, got " + sm.count);
        }
    }

    static void testNonRootGroupNoCheck() {
        RecordingManager sm = new RecordingManager(true);
        ThreadGroup child = new ThreadGroup("witup-child");
        sm.checkAccess(child);
        if (sm.count != 0) {
            throw new RuntimeException("checkPermission called for non-root group");
        }
    }

    public static void main(String[] args) {
        testNullGroup();
        testRootGroupAllowed();
        testRootGroupDenied();
        testNonRootGroupNoCheck();
    }
}
