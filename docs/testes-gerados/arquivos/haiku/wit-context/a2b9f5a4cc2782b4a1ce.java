/*
 * @test
 * @summary Exercises the PropertyPermissionCollection created through the public Permission.newPermissionCollection() path of PropertyPermission
 * @run main PropertyPermissionWitupTest
 */

import java.security.PermissionCollection;
import java.util.PropertyPermission;

public class PropertyPermissionWitupTest {

    public static void main(String[] args) {
        PermissionCollection pc = new PropertyPermission("java.home", "read").newPermissionCollection();
        if (pc == null) {
            throw new RuntimeException("newPermissionCollection() returned null");
        }
        pc.add(new PropertyPermission("java.home", "read"));
        if (!pc.implies(new PropertyPermission("java.home", "read"))) {
            throw new RuntimeException("collection does not imply added permission");
        }
        if (pc.implies(new PropertyPermission("user.dir", "read"))) {
            throw new RuntimeException("collection implies permission that was never added");
        }

        PermissionCollection wildcard = new PropertyPermission("java.*", "read").newPermissionCollection();
        wildcard.add(new PropertyPermission("java.*", "read"));
        if (!wildcard.implies(new PropertyPermission("java.home", "read"))) {
            throw new RuntimeException("wildcard collection does not imply java.home");
        }
        if (wildcard.implies(new PropertyPermission("user.dir", "read"))) {
            throw new RuntimeException("wildcard collection implies user.dir");
        }

        PermissionCollection second = new PropertyPermission("java.home", "read").newPermissionCollection();
        if (second == pc) {
            throw new RuntimeException("newPermissionCollection() returned a shared instance");
        }
        if (second.implies(new PropertyPermission("java.home", "read"))) {
            throw new RuntimeException("new collection carries state from previous collection");
        }
    }
}
