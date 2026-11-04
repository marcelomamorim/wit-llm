/*
 * @test
 * @summary Basic behavior of Permissions.add: stored permissions are implied, AllPermission implies all, and null is rejected
 * @run main PermissionsWitupTest
 */

import java.security.AllPermission;
import java.security.Permission;
import java.security.Permissions;
import java.util.Enumeration;

public class PermissionsWitupTest {

    public static void main(String[] args) throws Exception {
        testAddThenImplies();
        testAddSamePermissionTwice();
        testAddAllPermissionImpliesOthers();
        testAddedPermissionIsEnumerated();
        testAddNullThrowsNullPointerException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAddThenImplies() {
        Permissions perms = new Permissions();
        RuntimePermission rp = new RuntimePermission("exitVM");
        perms.add(rp);
        check(perms.implies(rp), "added permission must be implied");
        check(!perms.implies(new RuntimePermission("setIO")),
              "unrelated permission must not be implied");
    }

    static void testAddSamePermissionTwice() {
        Permissions perms = new Permissions();
        RuntimePermission rp = new RuntimePermission("exitVM");
        perms.add(rp);
        perms.add(rp);
        check(perms.implies(rp), "permission must remain implied after duplicate add");
    }

    static void testAddAllPermissionImpliesOthers() {
        Permissions perms = new Permissions();
        perms.add(new AllPermission());
        check(perms.implies(new RuntimePermission("exitVM")),
              "AllPermission must imply RuntimePermission");
    }

    static void testAddedPermissionIsEnumerated() {
        Permissions perms = new Permissions();
        RuntimePermission rp = new RuntimePermission("exitVM");
        perms.add(rp);
        Enumeration<Permission> e = perms.elements();
        boolean found = false;
        while (e.hasMoreElements()) {
            if (rp.equals(e.nextElement())) {
                found = true;
            }
        }
        check(found, "added permission must appear in elements()");
    }

    static void testAddNullThrowsNullPointerException() {
        Permissions perms = new Permissions();
        try {
            perms.add(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("add(null) must throw NullPointerException");
    }
}
