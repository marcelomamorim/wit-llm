/*
 * @test
 * @summary Verify that Permissions.add rejects new permissions when the collection is read-only and accepts them otherwise
 * @run main PermissionsWitupTest
 */

import java.lang.RuntimePermission;
import java.security.Permissions;

public class PermissionsWitupTest {

    public static void main(String[] args) {
        testAddOnReadOnlyThrowsSecurityException();
        testAddOnWritablePermissionsSucceeds();
    }

    private static void testAddOnReadOnlyThrowsSecurityException() {
        Permissions perms = new Permissions();
        perms.setReadOnly();
        if (!perms.isReadOnly()) {
            throw new AssertionError("collection should be read-only");
        }
        boolean thrown = false;
        try {
            perms.add(new RuntimePermission("exitVM"));
        } catch (SecurityException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected SecurityException when adding to read-only Permissions");
        }
    }

    private static void testAddOnWritablePermissionsSucceeds() {
        Permissions perms = new Permissions();
        if (perms.isReadOnly()) {
            throw new AssertionError("new Permissions should not be read-only");
        }
        perms.add(new RuntimePermission("exitVM"));
    }
}
