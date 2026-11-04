/*
 * @test
 * @summary Tests the add method of java.security.Permissions for normal and exceptional behavior.
 * @run main PermissionsWitupTest
 */

import java.security.Permissions;
import java.security.Permission;
import java.security.AllPermission;
import java.security.UnresolvedPermission;

public class PermissionsWitupTest {

    public static void main(String[] args) {
        testAddWithReadOnlyPermissions();
        testAddWithNormalPermission();
        testAddWithAllPermission();
        testAddWithUnresolvedPermission();
    }

    static void testAddWithReadOnlyPermissions() {
        Permissions perms = new Permissions();
        // Simulate read-only state by subclassing or reflection if needed; here, we assume isReadOnly() returns true
        // Since isReadOnly() is not public, we cannot directly set read-only; instead, we test the exception path by mocking or subclassing
        // For simplicity, assume the method isReadOnly() returns true if a certain condition is met; here, we simulate by subclassing
        Permissions readOnlyPerms = new Permissions() {
            @Override
            public boolean isReadOnly() {
                return true;
            }
        };
        try {
            readOnlyPerms.add(new Permission() {
                @Override
                public String getActions() { return ""; }
            });
            System.out.println("FAIL: Expected SecurityException not thrown in read-only add.");
        } catch (SecurityException se) {
            System.out.println("PASS: SecurityException thrown as expected in read-only add.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testAddWithNormalPermission() {
        Permissions perms = new Permissions();
        Permission perm = new Permission() {
            @Override
            public String getActions() { return ""; }
        };
        try {
            perms.add(perm);
            System.out.println("PASS: add() completed without exception for normal permission.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testAddWithAllPermission() {
        Permissions perms = new Permissions();
        AllPermission allPerm = new AllPermission();
        try {
            perms.add(allPerm);
            System.out.println("PASS: add() completed with AllPermission.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testAddWithUnresolvedPermission() {
        Permissions perms = new Permissions();
        UnresolvedPermission unresolvedPerm = new UnresolvedPermission("type", "name", "actions", null);
        try {
            perms.add(unresolvedPerm);
            System.out.println("PASS: add() completed with UnresolvedPermission.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
