/*
 * @test
 * @summary Test PropertyPermissionCollection constructor initializes fields correctly
 * @run main PropertyPermissionWitupTest
 */

import java.util.PropertyPermission;
import java.util.concurrent.ConcurrentHashMap;

public class PropertyPermissionWitupTest {
    public static void main(String[] args) {
        PropertyPermission.PropertyPermissionCollection ppc = new PropertyPermission.PropertyPermissionCollection();
        // Check that perms is not null and is a ConcurrentHashMap
        if (ppc.perms == null) {
            throw new RuntimeException("perms map is null");
        }
        if (!(ppc.perms instanceof ConcurrentHashMap)) {
            throw new RuntimeException("perms map is not a ConcurrentHashMap");
        }
        // Check that all_allowed is false
        if (ppc.all_allowed) {
            throw new RuntimeException("all_allowed should be false initially");
        }
    }
}
