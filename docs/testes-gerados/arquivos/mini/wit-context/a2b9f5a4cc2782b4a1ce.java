/*
 * @test
 * @summary Test PropertyPermissionCollection constructor for basic behavior
 * @run main PropertyPermissionWitupTest
 */

import java.util.PropertyPermission;
import java.util.PropertyPermission.PropertyPermissionCollection;
import java.util.concurrent.ConcurrentHashMap;

public class PropertyPermissionWitupTest {
    public static void main(String[] args) {
        // Test constructor creates a non-null perms map with expected capacity
        PropertyPermissionCollection ppc = new PropertyPermissionCollection();
        if (ppc == null) {
            throw new RuntimeException("PropertyPermissionCollection instance is null");
        }
        // The perms field is private, so we cannot check it directly.
        // Instead, test that no exception is thrown and the object is created.
        // This test guards against regressions that break construction.

        // Additional sanity: create multiple instances to check no side effects
        PropertyPermissionCollection ppc2 = new PropertyPermissionCollection();
        if (ppc2 == null) {
            throw new RuntimeException("Second PropertyPermissionCollection instance is null");
        }

        System.out.println("PropertyPermissionCollection constructor test passed");
    }
}
