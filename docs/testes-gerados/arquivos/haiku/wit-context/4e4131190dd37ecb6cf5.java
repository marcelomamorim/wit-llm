/*
 * @test
 * @summary Verifies SecurityManager.checkLink null handling and delegation to checkPermission with loadLibrary permission
 * @run main SecurityManagerWitupTest
 */

import java.security.Permission;

public class SecurityManagerWitupTest {

    static class RecordingSecurityManager extends SecurityManager {
        Permission last;

        @Override
        public void checkPermission(Permission perm) {
            last = perm;
        }
    }

    static class DenyingSecurityManager extends SecurityManager {
        @Override
        public void checkPermission(Permission perm) {
            throw new SecurityException("denied: " + perm.getName());
        }
    }

    static void checkNullThrowsNPE() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkLink(null);
            throw new RuntimeException("expected NullPointerException for null library");
        } catch (NullPointerException e) {
            if (!"library can't be null".equals(e.getMessage())) {
                throw new RuntimeException("unexpected NPE message: " + e.getMessage());
            }
        }
    }

    static void checkNonNullDelegatesToCheckPermission() {
        RecordingSecurityManager sm = new RecordingSecurityManager();
        sm.checkLink("foo");
        if (sm.last == null) {
            throw new RuntimeException("checkPermission was not invoked");
        }
        if (!(sm.last instanceof RuntimePermission)) {
            throw new RuntimeException("unexpected permission type: " + sm.last.getClass());
        }
        if (!"loadLibrary.foo".equals(sm.last.getName())) {
            throw new RuntimeException("unexpected permission name: " + sm.last.getName());
        }
    }

    static void checkDenialPropagates() {
        DenyingSecurityManager sm = new DenyingSecurityManager();
        try {
            sm.checkLink("bar");
            throw new RuntimeException("expected SecurityException");
        } catch (SecurityException e) {
            if (!"denied: loadLibrary.bar".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        checkNullThrowsNPE();
        checkNonNullDelegatesToCheckPermission();
        checkDenialPropagates();
    }
}
