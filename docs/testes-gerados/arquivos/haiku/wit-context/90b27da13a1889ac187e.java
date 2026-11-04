/*
 * @test
 * @summary Verifies observable behavior of Permissions.add for null and valid permissions
 * @run main PermissionsWitupTest
 */

import java.security.Permission;
import java.security.Permissions;
import java.util.Enumeration;

public class PermissionsWitupTest {

    public static void main(String[] args) {
        testNullPermissionThrowsNPE();
        testAddedPermissionIsImplied();
        testAddedPermissionAppearsInElements();
    }

    static void testNullPermissionThrowsNPE() {
        Permissions perms = new Permissions();
        try {
            perms.add(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null permission");
    }

    static void testAddedPermissionIsImplied() {
        Permissions perms = new Permissions();
        Permission p = new RuntimePermission("exitVM");
        perms.add(p);
        if (!perms.implies(p)) {
            throw new RuntimeException("Added permission should be implied");
        }
        if (perms.implies(new RuntimePermission("setIO"))) {
            throw new RuntimeException("Unrelated permission should not be implied");
        }
    }

    static void testAddedPermissionAppearsInElements() {
        Permissions perms = new Permissions();
        Permission p = new RuntimePermission("exitVM");
        perms.add(p);
        Enumeration<Permission> e = perms.elements();
        if (!e.hasMoreElements()) {
            throw new RuntimeException("Enumeration should contain added permission");
        }
        Permission found = e.nextElement();
        if (!p.equals(found)) {
            throw new RuntimeException("Enumerated permission differs from added permission");
        }
    }
}
