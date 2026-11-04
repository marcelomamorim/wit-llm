/*
 * @test
 * @summary Test sun.security.provider.PolicyFile.getPermissions(ProtectionDomain) method
 * @run main PolicyFileWitupTest
 */

import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.ProtectionDomain;
import java.security.Permission;
import java.io.FilePermission;

public class PolicyFileWitupTest {
    public static void main(String[] args) throws Exception {
        PolicyFileWitupTest test = new PolicyFileWitupTest();
        test.testGetPermissionsWithNullDomain();
        test.testGetPermissionsWithEmptyPermissions();
        test.testGetPermissionsWithStaticPermissions();
    }

    // Test that getPermissions returns empty Permissions when domain is null
    public void testGetPermissionsWithNullDomain() {
        sun.security.provider.PolicyFile policy = new sun.security.provider.PolicyFile();
        PermissionCollection perms = policy.getPermissions(null);
        if (perms == null) {
            throw new RuntimeException("Returned PermissionCollection is null");
        }
        if (perms.elements().hasMoreElements()) {
            throw new RuntimeException("Expected empty PermissionCollection for null domain");
        }
    }

    // Test getPermissions with a ProtectionDomain that has no static permissions
    public void testGetPermissionsWithEmptyPermissions() {
        sun.security.provider.PolicyFile policy = new sun.security.provider.PolicyFile();
        ProtectionDomain domain = new ProtectionDomain(null, null);
        PermissionCollection perms = policy.getPermissions(domain);
        if (perms == null) {
            throw new RuntimeException("Returned PermissionCollection is null");
        }
        // The returned permissions should at least be empty, no exception
        if (perms.elements().hasMoreElements()) {
            throw new RuntimeException("Expected empty PermissionCollection for domain with no permissions");
        }
    }

    // Test getPermissions with a ProtectionDomain that has static permissions
    public void testGetPermissionsWithStaticPermissions() {
        sun.security.provider.PolicyFile policy = new sun.security.provider.PolicyFile();
        Permissions staticPerms = new Permissions();
        FilePermission fp = new FilePermission("/tmp/*", "read");
        staticPerms.add(fp);

        ProtectionDomain domain = new ProtectionDomain(null, staticPerms);
        PermissionCollection perms = policy.getPermissions(domain);
        if (perms == null) {
            throw new RuntimeException("Returned PermissionCollection is null");
        }

        boolean found = false;
        for (java.util.Enumeration<Permission> e = perms.elements(); e.hasMoreElements();) {
            Permission p = e.nextElement();
            if (p.implies(fp)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new RuntimeException("Expected permission not found in returned PermissionCollection");
        }
    }
}
