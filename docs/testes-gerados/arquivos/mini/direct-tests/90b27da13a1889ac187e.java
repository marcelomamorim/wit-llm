/*
 * @test
 * @summary Test java.security.Permissions add(Permission) method basic behavior
 * @run main PermissionsWitupTest
 */

import java.security.Permissions;
import java.security.Permission;

public class PermissionsWitupTest {
    public static void main(String[] args) {
        Permissions perms = new Permissions();

        // Define a simple Permission subclass for testing
        class TestPermission extends Permission {
            public TestPermission(String name) {
                super(name);
            }
            public boolean implies(Permission p) {
                return false;
            }
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (!(obj instanceof TestPermission)) return false;
                TestPermission other = (TestPermission) obj;
                return this.getName().equals(other.getName());
            }
            public int hashCode() {
                return getName().hashCode();
            }
            public String getActions() {
                return "";
            }
        }

        TestPermission p1 = new TestPermission("perm1");
        TestPermission p2 = new TestPermission("perm2");

        // Add first permission
        perms.add(p1);

        // Add second permission
        perms.add(p2);

        // Add same permission again
        perms.add(p1);

        // Check that no exceptions thrown and add accepted
        // Since Permissions does not expose direct query, we test that adding twice does not fail

        // Also test that adding null throws NullPointerException
        boolean npeThrown = false;
        try {
            perms.add(null);
        } catch (NullPointerException npe) {
            npeThrown = true;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception when adding null", e);
        }

        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when adding null permission");
        }
    }
}
