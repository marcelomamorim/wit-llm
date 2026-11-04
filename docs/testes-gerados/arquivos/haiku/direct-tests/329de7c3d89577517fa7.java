/*
 * @test
 * @summary Checks PolicyFile.getPermissions(ProtectionDomain) for null domain and for a domain with static permissions
 * @modules java.base/sun.security.provider
 * @run main PolicyFileWitupTest
 */

import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.ProtectionDomain;
import java.security.RuntimePermission;
import sun.security.provider.PolicyFile;

public class PolicyFileWitupTest {
    public static void main(String[] args) throws Exception {
        PolicyFile pf = new PolicyFile();

        PermissionCollection nullResult = pf.getPermissions(null);
        if (nullResult == null) {
            throw new RuntimeException("getPermissions(null) returned null");
        }
        if (nullResult.elements().hasMoreElements()) {
            throw new RuntimeException("getPermissions(null) returned non-empty collection");
        }

        Permissions staticPerms = new Permissions();
        RuntimePermission rp = new RuntimePermission("witupStaticPerm");
        staticPerms.add(rp);
        ProtectionDomain pd = new ProtectionDomain(null, staticPerms);

        PermissionCollection result = pf.getPermissions(pd);
        if (result == null) {
            throw new RuntimeException("getPermissions(domain) returned null");
        }
        if (result == staticPerms) {
            throw new RuntimeException("getPermissions(domain) must return a new collection");
        }
        if (!result.implies(rp)) {
            throw new RuntimeException("static permission missing from result");
        }
    }
}
