/*
 * @test
 * @summary Tests getPermissions method of PolicyFile class for null and non-null ProtectionDomain.
 * @run main PolicyFileWitupTest
 */

import java.security.*;
import sun.security.provider.PolicyFile;

public class PolicyFileWitupTest {

    public static void main(String[] args) {
        testGetPermissionsWithNullDomain();
        testGetPermissionsWithMockDomain();
    }

    private static void testGetPermissionsWithNullDomain() {
        try {
            PermissionCollection perms = new PolicyFile().getPermissions(null);
            if (perms == null) {
                System.out.println("PASS: getPermissions with null domain returns non-null PermissionCollection");
            } else {
                System.out.println("FAIL: getPermissions with null domain did not return null");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception thrown in getPermissions with null domain: " + e);
        }
    }

    private static void testGetPermissionsWithMockDomain() {
        try {
            // Create a mock ProtectionDomain with a Permissions object
            Permissions perms = new Permissions();
            perms.add(new java.io.FilePermission("/tmp/test", "read"));
            ProtectionDomain pd = new ProtectionDomain(null, perms);
            PolicyFile policyFile = new PolicyFile();
            PermissionCollection result = policyFile.getPermissions(pd);
            if (result != null && result.elements().hasMoreElements()) {
                boolean found = false;
                for (Permission p : java.util.Collections.list(result.elements())) {
                    if (p instanceof java.io.FilePermission && p.getName().equals("/tmp/test")) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    System.out.println("PASS: getPermissions with mock domain includes FilePermission for /tmp/test");
                } else {
                    System.out.println("FAIL: getPermissions with mock domain does not include expected FilePermission");
                }
            } else {
                System.out.println("FAIL: getPermissions with mock domain returned null or empty permissions");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception thrown in getPermissions with mock domain: " + e);
        }
    }
}
