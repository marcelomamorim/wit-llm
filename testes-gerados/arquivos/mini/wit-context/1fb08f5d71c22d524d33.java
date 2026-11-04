/*
 * @test
 * @summary Test java.security.Policy.add(Permission) method behavior
 * @run main PolicyWitupTest
 */

import java.security.Policy;
import java.security.Permission;
import java.security.Permissions;
import java.security.SecurityException;

public class PolicyWitupTest {
    public static void main(String[] args) {
        testAddPermissionToNonReadOnlyPolicy();
        testAddPermissionToReadOnlyPolicyThrows();
    }

    private static void testAddPermissionToNonReadOnlyPolicy() {
        // Create a Permissions instance (which extends Policy) to test add
        Permissions permissions = new Permissions();
        Permission perm = new RuntimePermission("testPermission");
        try {
            permissions.add(perm);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception when adding permission to non-readOnly policy", e);
        }
    }

    private static void testAddPermissionToReadOnlyPolicyThrows() {
        // The UnsupportedEmptyCollection is internal, but Permissions uses it when readOnly is true
        Permissions permissions = new Permissions();
        Permission perm = new RuntimePermission("testPermission");
        // Set readOnly to true by calling setReadOnly()
        permissions.setReadOnly();
        boolean thrown = false;
        try {
            permissions.add(perm);
        } catch (SecurityException se) {
            thrown = true;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
        if (!thrown) {
            throw new RuntimeException("Expected SecurityException not thrown when adding permission to readOnly policy");
        }
    }
}
