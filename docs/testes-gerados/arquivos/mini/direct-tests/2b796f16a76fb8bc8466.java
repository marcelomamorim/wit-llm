/*
 * @test
 * @summary Test java.security.Permissions.add(Permission) method behavior
 * @run main PermissionsWitupTest
 */

import java.security.Permissions;
import java.security.Permission;
import java.security.AllPermission;
import java.security.UnresolvedPermission;
import java.security.SecurityException;

public class PermissionsWitupTest {
    public static void main(String[] args) throws Exception {
        testAddNormalPermission();
        testAddAllPermissionSetsAllPermissionField();
        testAddUnresolvedPermissionSetsHasUnresolvedFlag();
        testAddToReadOnlyThrows();
    }

    static void testAddNormalPermission() {
        Permissions perms = new Permissions();
        Permission p = new DummyPermission("test");
        perms.add(p);
        // The permission should be contained in the Permissions object
        if (!perms.implies(p)) {
            throw new RuntimeException("Added permission not implied by Permissions");
        }
    }

    static void testAddAllPermissionSetsAllPermissionField() {
        Permissions perms = new Permissions();
        AllPermission allPerm = new AllPermission();
        perms.add(allPerm);
        // The allPermission field should be set and imply the AllPermission
        if (!perms.implies(allPerm)) {
            throw new RuntimeException("AllPermission not implied after add");
        }
    }

    static void testAddUnresolvedPermissionSetsHasUnresolvedFlag() {
        Permissions perms = new Permissions();
        UnresolvedPermission unresolved = new UnresolvedPermission("java.io.FilePermission", "<<ALL FILES>>", "read,write", null);
        perms.add(unresolved);
        // We cannot access hasUnresolved directly, but adding UnresolvedPermission
        // should not cause exceptions and implies should return false for unrelated
        if (perms.implies(unresolved)) {
            throw new RuntimeException("UnresolvedPermission should not be implied");
        }
    }

    static void testAddToReadOnlyThrows() {
        Permissions perms = new Permissions();
        perms.setReadOnly();
        try {
            perms.add(new DummyPermission("fail"));
            throw new RuntimeException("Expected SecurityException when adding to read-only");
        } catch (SecurityException e) {
            // expected
        }
    }

    // Dummy Permission subclass for testing
    static final class DummyPermission extends Permission {
        DummyPermission(String name) {
            super(name);
        }
        public boolean implies(Permission p) {
            return false;
        }
        public boolean equals(Object obj) {
            if (!(obj instanceof DummyPermission)) return false;
            DummyPermission other = (DummyPermission) obj;
            return this.getName().equals(other.getName());
        }
        public int hashCode() {
            return getName().hashCode();
        }
        public String getActions() {
            return "";
        }
    }
}
