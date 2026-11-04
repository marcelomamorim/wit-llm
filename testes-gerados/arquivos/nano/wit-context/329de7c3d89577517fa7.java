/*
 * @test
 * @summary Tests for sun.security.provider.PolicyFile.getPermissions(ProtectionDomain)
 * @run main PolicyFileWitupTest
 */

import java.security.*;
import sun.security.provider.PolicyFile;

public class PolicyFileWitupTest {

    public static void main(String[] args) {
        testNullProtectionDomain();
        testValidProtectionDomain();
        testProtectionDomainWithPermissions();
    }

    private static void testNullProtectionDomain() {
        try {
            PermissionCollection perms = PolicyFile.getPermissions(null);
            if (perms == null) {
                System.out.println("PASS: getPermissions(null) returned null as expected.");
            } else {
                System.out.println("FAIL: getPermissions(null) did not return null.");
            }
        } catch (Exception e) {
            System.out.println("FAIL: getPermissions(null) threw exception: " + e);
        }
    }

    private static void testValidProtectionDomain() {
        try {
            ProtectionDomain pd = new ProtectionDomain(null, null);
            PermissionCollection perms = PolicyFile.getPermissions(pd);
            if (perms != null) {
                System.out.println("PASS: getPermissions(pd) returned a PermissionCollection.");
            } else {
                System.out.println("FAIL: getPermissions(pd) returned null.");
            }
        } catch (Exception e) {
            System.out.println("FAIL: getPermissions(pd) threw exception: " + e);
        }
    }

    private static void testProtectionDomainWithPermissions() {
        try {
            Permissions perms = new Permissions();
            perms.add(new java.io.FilePermission("/tmp/testfile", "read"));
            ProtectionDomain pd = new ProtectionDomain(null, perms);
            PermissionCollection resultPerms = PolicyFile.getPermissions(pd);
            if (resultPerms != null && resultPerms.implies(new java.io.FilePermission("/tmp/testfile", "read"))) {
                System.out.println("PASS: getPermissions(pd) includes added permissions.");
            } else {
                System.out.println("FAIL: getPermissions(pd) does not include added permissions.");

            }
        } catch (Exception e) {
            System.out.println("FAIL: getPermissions(pd) threw exception: " + e);
        }
    }
}
