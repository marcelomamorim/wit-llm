/* @test    @summary Test the add method of Permissions class for correct behavior.    @run main PermissionsWitupTest */

import java.security.Permissions;
import java.security.Permission;
import java.security.AllPermission;
import java.security.SecurityPermission;

public class PermissionsWitupTest {

    public static void main(String[] args) {
        testAddSinglePermission();
        testAddMultiplePermissions();
        testAddNullPermission();
    }

    static void testAddSinglePermission() {
        Permissions permissions = new Permissions();
        Permission perm = new SecurityPermission("setPolicy");
        permissions.add(perm);
        boolean found = false;
        for (Permission p : permissions.elements()) {
            if (p.equals(perm)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AssertionError("Permission not found after add.");
        }
    }

    static void testAddMultiplePermissions() {
        Permissions permissions = new Permissions();
        Permission perm1 = new SecurityPermission("setPolicy");
        Permission perm2 = new AllPermission();
        permissions.add(perm1);
        permissions.add(perm2);
        boolean foundPerm1 = false;
        boolean foundPerm2 = false;
        for (Permission p : permissions.elements()) {
            if (p.equals(perm1)) {
                foundPerm1 = true;
            }
            if (p.equals(perm2)) {
                foundPerm2 = true;
            }
        }
        if (!foundPerm1 || !foundPerm2) {
            throw new AssertionError("One or more permissions not found after add.");
        }
    }

    static void testAddNullPermission() {
        Permissions permissions = new Permissions();
        try {
            permissions.add(null);
            throw new AssertionError("Adding null permission should throw NullPointerException.");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
