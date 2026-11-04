/*
 * @test
 * @summary Test java.security.Permissions.add(Permission) for readOnly and normal add
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
        testAddAllPermission();
        testAddUnresolvedPermission();
        testAddReadOnlyThrows();
    }

    private static void testAddNormalPermission() {
        Permissions perms = new Permissions();
        Permission p = new DummyPermission("test");
        perms.add(p);
        // No exception means success
    }

    private static void testAddAllPermission() {
        Permissions perms = new Permissions();
        Permission allPerm = AllPermission.INSTANCE;
        perms.add(allPerm);
        // No exception means success
    }

    private static void testAddUnresolvedPermission() {
        Permissions perms = new Permissions();
        Permission unresolved = new UnresolvedPermission("java.io.FilePermission", "/tmp/*", "read", null);
        perms.add(unresolved);
        // No exception means success
    }

    private static void testAddReadOnlyThrows() {
        Permissions perms = new Permissions();
        perms.setReadOnly();
        Permission p = new DummyPermission("readonly");
        boolean thrown = false;
        try {
            perms.add(p);
        } catch (SecurityException se) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected SecurityException when adding to readOnly Permissions");
        }
    }

    // Dummy Permission subclass for testing
    private static final class DummyPermission extends Permission {
        DummyPermission(String name) {
            super(name);
        }
        public boolean implies(Permission p) {
            return false;
        }
        public boolean equals(Object obj) {
            if (this == obj) return true;
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
