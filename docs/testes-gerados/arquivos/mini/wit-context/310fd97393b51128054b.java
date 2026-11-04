/*
 * @test
 * @summary Test java.security.Permissions.nextElement() for expected behavior and exception
 * @run main PermissionsWitupTest
 */

import java.security.Permissions;
import java.security.Permission;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class PermissionsWitupTest {
    public static void main(String[] args) {
        testNextElementThrowsWhenEmpty();
    }

    private static void testNextElementThrowsWhenEmpty() {
        Permissions permissions = new Permissions();
        Enumeration<Permission> e = permissions.elements();
        boolean threw = false;
        try {
            // nextElement should throw NoSuchElementException when no elements
            e.nextElement();
        } catch (NoSuchElementException ex) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NoSuchElementException when calling nextElement on empty Permissions");
        }
    }
}
