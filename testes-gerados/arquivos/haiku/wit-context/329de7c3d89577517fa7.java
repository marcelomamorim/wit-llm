/*
 * @test
 * @summary Witup-generated checks for PolicyFile.getPermissions(ProtectionDomain)
 * @modules java.base/sun.security.provider
 * @run main PolicyFileWitupTest
 */

import java.security.Permissions;
import java.security.PermissionCollection;
import java.security.ProtectionDomain;
import java.security.RuntimePermission;
import sun.security.provider.PolicyFile;

public class PolicyFileWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) throws Exception {
        PolicyFile pf = new PolicyFile();

        PermissionCollection nullDomainPerms = pf.getPermissions((ProtectionDomain) null);
        check(nullDomainPerms != null, "null domain must return a non-null collection");
        check(!nullDomainPerms.elements().hasMoreElements(),
              "null domain must return an empty collection");

        Permissions staticPerms = new Permissions();
        staticPerms.add(new RuntimePermission("witupStaticPerm"));
        ProtectionDomain pd = new ProtectionDomain(null, staticPerms);
        PermissionCollection result = pf.getPermissions(pd);
        check(result != null, "domain with static permissions must return a non-null collection");
        check(result.implies(new RuntimePermission("witupStaticPerm")),
              "static permissions of the domain must be included in the result");

        ProtectionDomain emptyPd = new ProtectionDomain(null, null);
        PermissionCollection emptyResult = pf.getPermissions(emptyPd);
        check(emptyResult != null, "domain without static permissions must return a non-null collection");

        PermissionCollection second = pf.getPermissions(pd);
        check(second != result, "each call must return a new collection");
    }
}
