/* @test    @summary Test the add method of java.security.Permissions for basic functionality and exception handling    @run main PermissionsAddMethodTest */

import java.security.Permissions;
import java.security.Permission;
import java.security.AllPermission;
import java.security.UnresolvedPermission;

public class PermissionsAddMethodTest {

    public static void main(String[] args) {
        testAddNormalPermission();
        testAddAllPermission();
        testAddUnresolvedPermission();
        testAddToReadOnlyPermissions();
    }

    static void testAddNormalPermission() {
        Permissions perms = new Permissions();
        Permission perm = new DummyPermission("perm1");
        perms.add(perm);
        boolean contains = false;
        for (Permission p : perms.elements()) {
            if (p.equals(perm)) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            throw new AssertionError("Normal permission not added correctly");
        }
    }

    static void testAddAllPermission() {
        Permissions perms = new Permissions();
        Permission allPerm = new AllPermission();
        perms.add(allPerm);
        boolean found = false;
        for (Permission p : perms.elements()) {
            if (p instanceof AllPermission) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AssertionError("AllPermission not added correctly");
        }
    }

    static void testAddUnresolvedPermission() {
        Permissions perms = new Permissions();
        Permission unresolvedPerm = new UnresolvedPermission("type", "name", null, null);
        perms.add(unresolvedPerm);
        boolean found = false;
        for (Permission p : perms.elements()) {
            if (p.equals(unresolvedPerm)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AssertionError("UnresolvedPermission not added correctly");
        }
    }

    static void testAddToReadOnlyPermissions() {
        Permissions perms = new Permissions();
        perms.setReadOnly();
        try {
            perms.add(new DummyPermission("perm2"));
            throw new AssertionError("Expected SecurityException not thrown");
        } catch (SecurityException se) {
            // expected
        }
    }

    static class DummyPermission extends Permission {
        public DummyPermission(String name) {
            super(name);
        }
        @Override
        public boolean implies(Permission p) {
            return false;
        }
        @Override
        public String getActions() {
            return "";
        }
    }
}
