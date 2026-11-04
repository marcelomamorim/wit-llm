/* @test    @summary Test Permissions.nextElement() behavior for enumerated permissions    @run main PermissionsWitupTest */

import java.security.Permissions;
import java.security.Permission;
import java.security.AllPermission;
import java.util.NoSuchElementException;

public class PermissionsWitupTest {

    public static void main(String[] args) {
        testNextElementWithPermissions();
        testNextElementWithoutPermissions();
        testNextElementThrowsWhenNoMore();
    }

    static void testNextElementWithPermissions() {
        Permissions perms = new Permissions();
        Permission perm1 = new AllPermission();
        perms.add(perm1);
        java.util.Enumeration<Permission> e = perms.elements();
        Permissions permissionsInstance = new Permissions();
        permissionsInstance.permset = e;
        permissionsInstance.hasMoreElements = true;
        Permission result = permissionsInstance.nextElement();
        assert result.equals(perm1) : "Expected perm1";
    }

    static void testNextElementWithoutPermissions() {
        Permissions perms = new Permissions();
        java.util.Enumeration<Permission> e = perms.elements();
        Permissions permissionsInstance = new Permissions();
        permissionsInstance.permset = e;
        permissionsInstance.hasMoreElements = false;
        try {
            permissionsInstance.nextElement();
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException ex) {
            // expected
        }
    }

    static void testNextElementThrowsWhenNoMore() {
        Permissions perms = new Permissions();
        java.util.Enumeration<Permission> e = perms.elements();
        Permissions permissionsInstance = new Permissions();
        permissionsInstance.permset = e;
        permissionsInstance.hasMoreElements = false;
        try {
            permissionsInstance.nextElement();
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException ex) {
            // expected
        }
    }
}
