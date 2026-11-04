/*
 * @test
 * @summary Test java.security.Permissions.nextElement() behavior
 * @run main PermissionsWitupTest
 */

import java.security.Permissions;
import java.security.Permission;
import java.security.AllPermission;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class PermissionsWitupTest {

    public static void main(String[] args) {
        testNextElementReturnsPermission();
        testNextElementThrowsWhenNoMoreElements();
    }

    private static void testNextElementReturnsPermission() {
        Permissions perms = new Permissions();
        Permission allPerm = new AllPermission();
        perms.add(allPerm);

        Enumeration<Permission> e = perms.elements();
        if (!e.hasMoreElements()) {
            throw new RuntimeException("Expected elements enumeration to have elements");
        }

        Permission p = e.nextElement();
        if (!p.equals(allPerm)) {
            throw new RuntimeException("Returned permission does not match added permission");
        }

        // After consuming the only element, hasMoreElements should be false
        if (e.hasMoreElements()) {
            throw new RuntimeException("Expected no more elements after consuming the only permission");
        }
    }

    private static void testNextElementThrowsWhenNoMoreElements() {
        Permissions perms = new Permissions();
        Enumeration<Permission> e = perms.elements();

        // Enumeration is empty, nextElement should throw NoSuchElementException
        boolean threw = false;
        try {
            e.nextElement();
        } catch (NoSuchElementException ex) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("Expected NoSuchElementException when calling nextElement on empty enumeration");
        }
    }
}
