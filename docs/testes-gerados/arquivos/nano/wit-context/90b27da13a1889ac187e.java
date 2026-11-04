/* @test    @summary Test for Permissions.add(Permission) method to verify behavior with null input.    @run main PermissionsWitupTest */

import java.security.Permissions;
import java.security.Permission;

public class PermissionsWitupTest {

    public static void main(String[] args) {
        testAddWithNullPermission();
        testAddWithValidPermission();
    }

    private static void testAddWithNullPermission() {
        Permissions permissions = new Permissions();
        try {
            permissions.add(null);
            System.out.println("testAddWithNullPermission: FAILED - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testAddWithNullPermission: PASSED");
        } catch (Exception e) {
            System.out.println("testAddWithNullPermission: FAILED - Unexpected exception: " + e);
        }
    }

    private static void testAddWithValidPermission() {
        Permissions permissions = new Permissions();
        Permission perm = new Permission("test") {};
        permissions.add(perm);
        // Verify that the permission was added by checking if it exists in the collection
        boolean found = false;
        for (Permission p : permissions.elements()) {
            if (p.equals(perm)) {
                found = true;
                break;
            }
        }
        if (found) {
            System.out.println("testAddWithValidPermission: PASSED");
        } else {
            System.out.println("testAddWithValidPermission: FAILED - Permission not found after add");
        }
    }
}
