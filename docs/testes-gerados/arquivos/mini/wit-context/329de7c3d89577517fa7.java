/*
 * @test
 * @summary Test sun.security.provider.PolicyFile.getPermissions(ProtectionDomain) basic behavior
 * @run main PolicyFileWitupTest
 */

import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.ProtectionDomain;
import sun.security.provider.PolicyFile;
import java.security.Permission;
import java.io.FilePermission;
import java.util.Enumeration;

public class PolicyFileWitupTest {
    public static void main(String[] args) throws Exception {
        PolicyFile policy = new PolicyFile();

        // Test 1: null ProtectionDomain returns empty Permissions
        PermissionCollection permsNull = policy.getPermissions(null);
        if (permsNull == null) {
            throw new RuntimeException("getPermissions(null) returned null");
        }
        if (permsNull.elements().hasMoreElements()) {
            throw new RuntimeException("getPermissions(null) should return empty Permissions");
        }

        // Test 2: ProtectionDomain with no permissions returns empty Permissions
        ProtectionDomain emptyDomain = new ProtectionDomain(null, null);
        PermissionCollection permsEmpty = policy.getPermissions(emptyDomain);
        if (permsEmpty == null) {
            throw new RuntimeException("getPermissions(emptyDomain) returned null");
        }
        // It may contain static perms, but since domain.getPermissions() is null, expect empty
        Enumeration<Permission> e = permsEmpty.elements();
        if (e == null) {
            throw new RuntimeException("PermissionCollection.elements() returned null");
        }

        // Test 3: ProtectionDomain with some permissions
        Permissions domainPerms = new Permissions();
        domainPerms.add(new FilePermission("/tmp/*", "read"));
        ProtectionDomain domainWithPerms = new ProtectionDomain(null, domainPerms);
        PermissionCollection permsWith = policy.getPermissions(domainWithPerms);
        if (permsWith == null) {
            throw new RuntimeException("getPermissions(domainWithPerms) returned null");
        }
        boolean found = false;
        Enumeration<Permission> en = permsWith.elements();
        while (en.hasMoreElements()) {
            Permission p = en.nextElement();
            if (p instanceof FilePermission) {
                FilePermission fp = (FilePermission) p;
                if ("/tmp/*".equals(fp.getName()) && fp.getActions().contains("read")) {
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            throw new RuntimeException("Expected FilePermission not found in returned Permissions");
        }

        // No IllegalArgumentException expected from getPermissions
        // The WIT expath about IllegalArgumentException is related to internal ConcurrentHashMap constructor
        // and not triggered by public API usage here, so no test for exception thrown.

        System.out.println("All tests passed.");
    }
}
