/*
 * @test
 * @summary Test java.security.Permissions.add(Permission) for null argument and normal usage
 * @run main PermissionsWitupTest
 */

import java.security.Permissions;
import java.security.Permission;

public class PermissionsWitupTest {
    public static void main(String[] args) {
        Permissions perms = new Permissions();

        // Test that add(null) throws NullPointerException as per current implementation
        boolean npeThrown = false;
        try {
            perms.add(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when adding null permission");
        }

        // Test adding a dummy Permission subclass instance
        Permission dummyPerm = new Permission("dummy") {
            public boolean implies(Permission p) { return false; }
            public boolean equals(Object obj) { return this == obj; }
            public int hashCode() { return 1; }
            public String getActions() { return ""; }
        };

        perms.add(dummyPerm);

        // Verify that the permission is contained by iterating elements
        boolean found = false;
        for (java.util.Enumeration<Permission> e = perms.elements(); e.hasMoreElements(); ) {
            Permission p = e.nextElement();
            if (p.equals(dummyPerm)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new RuntimeException("Added permission not found in Permissions.elements()");
        }
    }
}
