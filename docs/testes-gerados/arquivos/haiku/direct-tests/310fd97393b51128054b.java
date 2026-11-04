/*
 * @test
 * @summary Verify Permissions.elements() nextElement behavior: NoSuchElementException when exhausted and returned permissions
 * @run main PermissionsWitupTest
 */

import java.security.Permission;
import java.security.Permissions;
import java.security.RuntimePermission;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class PermissionsWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptyNextElementThrows();
        testSingleElement();
        testMultipleElements();
    }

    static void testEmptyNextElementThrows() {
        Permissions p = new Permissions();
        Enumeration<Permission> e = p.elements();
        if (e.hasMoreElements()) {
            throw new RuntimeException("empty Permissions should have no elements");
        }
        boolean thrown = false;
        try {
            e.nextElement();
        } catch (NoSuchElementException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("nextElement on empty enumeration must throw NoSuchElementException");
        }
    }

    static void testSingleElement() {
        Permissions p = new Permissions();
        p.add(new RuntimePermission("witupA"));
        Enumeration<Permission> e = p.elements();
        if (!e.hasMoreElements()) {
            throw new RuntimeException("expected one element");
        }
        Permission perm = e.nextElement();
        if (!new RuntimePermission("witupA").equals(perm)) {
            throw new RuntimeException("unexpected permission returned: " + perm);
        }
        if (e.hasMoreElements()) {
            throw new RuntimeException("expected no further elements");
        }
        boolean thrown = false;
        try {
            e.nextElement();
        } catch (NoSuchElementException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("nextElement after exhaustion must throw NoSuchElementException");
        }
    }

    static void testMultipleElements() {
        Permissions p = new Permissions();
        p.add(new RuntimePermission("witupB"));
        p.add(new RuntimePermission("witupC"));
        Enumeration<Permission> e = p.elements();
        Set<String> names = new HashSet<>();
        int count = 0;
        while (e.hasMoreElements()) {
            Permission perm = e.nextElement();
            names.add(perm.getName());
            count++;
        }
        if (count != 2) {
            throw new RuntimeException("expected 2 elements but got " + count);
        }
        if (!names.contains("witupB") || !names.contains("witupC")) {
            throw new RuntimeException("missing expected permissions: " + names);
        }
        boolean thrown = false;
        try {
            e.nextElement();
        } catch (NoSuchElementException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("nextElement after exhaustion must throw NoSuchElementException");
        }
    }
}
