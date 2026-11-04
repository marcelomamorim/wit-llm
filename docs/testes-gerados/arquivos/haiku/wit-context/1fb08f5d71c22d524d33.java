/*
 * @test
 * @summary Verify that the default PermissionCollection returned by a Policy subclass rejects add() with SecurityException
 * @run main PolicyWitupTest
 */

import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.ProtectionDomain;
import java.security.SecurityPermission;
import java.security.RuntimePermission;

public class PolicyWitupTest {

    static class TestPolicy extends Policy {
        @Override
        public boolean implies(ProtectionDomain domain, Permission permission) {
            return false;
        }

        @Override
        public void refresh() {
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAddOnDefaultCollectionThrowsSecurityException() {
        Policy policy = new TestPolicy();
        PermissionCollection pc = policy.getPermissions((CodeSource) null);
        check(pc != null, "default permission collection must not be null");
        boolean thrown = false;
        try {
            pc.add(new RuntimePermission("exitVM"));
        } catch (SecurityException e) {
            thrown = true;
        }
        check(thrown, "add() on read-only default collection must throw SecurityException");
    }

    static void testAddSecurityPermissionThrowsSecurityException() {
        Policy policy = new TestPolicy();
        PermissionCollection pc = policy.getPermissions((CodeSource) null);
        boolean thrown = false;
        try {
            pc.add(new SecurityPermission("getPolicy"));
        } catch (SecurityException e) {
            thrown = true;
        }
        check(thrown, "add() of SecurityPermission on read-only collection must throw SecurityException");
    }

    public static void main(String[] args) {
        testAddOnDefaultCollectionThrowsSecurityException();
        testAddSecurityPermissionThrowsSecurityException();
    }
}
