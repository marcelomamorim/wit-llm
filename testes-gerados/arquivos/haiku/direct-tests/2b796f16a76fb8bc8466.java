/*
 * @test
 * @summary Basic observable behavior of Permissions.add, including readonly rejection
 * @run main PermissionsWitupTest
 */

import java.security.AllPermission;
import java.security.Permission;
import java.security.Permissions;
import java.security.UnresolvedPermission;
import java.util.Enumeration;

public class PermissionsWitupTest {

    public static void main(String[] args) throws Exception {
        testAddAndImplies();
        testAddAllPermission();
        testAddUnresolved();
        testReadOnlyRejectsAdd();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static int count(Enumeration<Permission> e) {
        int n = 0;
        while (e.hasMoreElements()) {
            e.nextElement();
            n++;
        }
        return n;
    }

    static void testAddAndImplies() {
        Permissions p = new Permissions();
        RuntimePermission exit = new RuntimePermission("exitVM");
        p.add(exit);
        check(p.implies(exit), "added permission must be implied");
        check(!p.implies(new RuntimePermission("createClassLoader")),
              "unrelated permission must not be implied");
        check(count(p.elements()) == 1, "expected one element after one add");

        p.add(new RuntimePermission("setIO"));
        check(count(p.elements()) == 2, "expected two elements after two adds");
    }

    static void testAddAllPermission() {
        Permissions p = new Permissions();
        p.add(new AllPermission());
        check(p.implies(new RuntimePermission("anything")),
              "AllPermission must imply any permission");
    }

    static void testAddUnresolved() {
        Permissions p = new Permissions();
        UnresolvedPermission up =
            new UnresolvedPermission("com.example.Missing", "name", null, null);
        p.add(up);
        check(count(p.elements()) == 1, "unresolved permission must be stored");
    }

    static void testReadOnlyRejectsAdd() {
        Permissions p = new Permissions();
        p.setReadOnly();
        check(p.isReadOnly(), "object must be readonly after setReadOnly");
        try {
            p.add(new RuntimePermission("exitVM"));
            throw new RuntimeException("add on readonly Permissions must throw");
        } catch (SecurityException expected) {
            // expected
        }
        check(count(p.elements()) == 0, "rejected add must not store permission");
    }
}
