/*
 * @test
 * @summary Basic public behavior of the Permissions no-arg constructor
 * @run main PermissionsWitupTest
 */

import java.security.Permission;
import java.security.Permissions;
import java.util.Enumeration;

public class PermissionsWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Permissions perms = new Permissions();

        check(!perms.isReadOnly(), "new Permissions should not be read-only");

        Enumeration<Permission> empty = perms.elements();
        check(!empty.hasMoreElements(), "new Permissions should have no elements");

        Permission exitVm = new RuntimePermission("exitVM");
        check(!perms.implies(exitVm), "empty Permissions should not imply exitVM");

        perms.add(exitVm);
        check(perms.implies(exitVm), "Permissions should imply added permission");
        check(!perms.implies(new RuntimePermission("setIO")),
              "Permissions should not imply unrelated permission");

        Enumeration<Permission> after = perms.elements();
        int count = 0;
        while (after.hasMoreElements()) {
            after.nextElement();
            count++;
        }
        check(count == 1, "expected exactly one element but found " + count);

        perms.setReadOnly();
        check(perms.isReadOnly(), "setReadOnly should make Permissions read-only");
        try {
            perms.add(new RuntimePermission("setIO"));
            throw new RuntimeException("add on read-only Permissions should throw SecurityException");
        } catch (SecurityException expected) {
            // expected
        }
    }
}
