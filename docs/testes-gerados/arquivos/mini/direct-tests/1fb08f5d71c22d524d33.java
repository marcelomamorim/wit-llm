/*
 * @test
 * @summary Test java.security.Policy add(Permission) method behavior
 * @run main PolicyWitupTest
 */

import java.security.Policy;
import java.security.Permission;
import java.security.BasicPermission;

public class PolicyWitupTest {
    public static void main(String[] args) {
        // Create a minimal subclass of Policy to test add(Permission)
        Policy policy = new Policy() {
            private java.util.List<Permission> perms = new java.util.ArrayList<>();
            @Override
            public void add(Permission permission) {
                perms.add(permission);
            }
            // Expose perms size for test
            public int getPermsSize() {
                return perms.size();
            }
            // Expose perms contains for test
            public boolean contains(Permission p) {
                return perms.contains(p);
            }
            @Override
            public boolean implies(java.security.ProtectionDomain domain, Permission permission) {
                return false;
            }
            @Override
            public void refresh() {
            }
        };

        // Test adding a valid permission
        Permission perm1 = new BasicPermission("testPermission") {
            private static final long serialVersionUID = 1L;
        };
        policy.add(perm1);
        if (((PolicyWitupTest.PolicyWithAdd)policy).getPermsSize() != 1) {
            throw new RuntimeException("Failed to add permission");
        }
        if (!((PolicyWitupTest.PolicyWithAdd)policy).contains(perm1)) {
            throw new RuntimeException("Permission not found after add");
        }

        // Test adding null permission - no exception expected as per given code
        try {
            policy.add(null);
        } catch (NullPointerException npe) {
            // Expected if underlying list does not accept null
            // But code does not explicitly check, so accept NPE
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception when adding null", e);
        }

        // Test adding multiple permissions
        Permission perm2 = new BasicPermission("anotherPermission") {
            private static final long serialVersionUID = 1L;
        };
        policy.add(perm2);
        if (((PolicyWitupTest.PolicyWithAdd)policy).getPermsSize() != 2) {
            throw new RuntimeException("Failed to add second permission");
        }
        if (!((PolicyWitupTest.PolicyWithAdd)policy).contains(perm2)) {
            throw new RuntimeException("Second permission not found after add");
        }
    }

    // Helper subclass to expose perms list for testing
    static abstract class PolicyWithAdd extends Policy {
        abstract void add(Permission permission);
        abstract int getPermsSize();
        abstract boolean contains(Permission p);
    }
}