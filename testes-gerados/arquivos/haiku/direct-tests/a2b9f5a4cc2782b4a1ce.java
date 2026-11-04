/*
 * @test
 * @summary PropertyPermissionCollection created via PropertyPermission.newPermissionCollection() must be a usable, independent, non-read-only collection
 * @run main PropertyPermissionWitupTest
 */

import java.security.PermissionCollection;
import java.util.Enumeration;

public class PropertyPermissionWitupTest {

    public static void main(String[] args) {
        PropertyPermission p = new PropertyPermission("java.home", "read");
        PermissionCollection pc = p.newPermissionCollection();
        check(pc != null, "collection must not be null");
        check(!pc.isReadOnly(), "new collection must not be read-only");
        check(!pc.implies(new PropertyPermission("user.dir", "read")), "empty collection must not imply anything");

        pc.add(new PropertyPermission("user.dir", "read"));
        check(pc.implies(new PropertyPermission("user.dir", "read")), "added permission must be implied");
        check(!pc.implies(new PropertyPermission("user.dir", "write")), "unadded action must not be implied");
        check(!pc.implies(new PropertyPermission("java.home", "read")), "unrelated name must not be implied");

        pc.add(new PropertyPermission("java.*", "read"));
        check(pc.implies(new PropertyPermission("java.version", "read")), "wildcard permission must imply matching name");
        check(!pc.implies(new PropertyPermission("os.name", "read")), "wildcard must not match other prefixes");

        int count = 0;
        Enumeration<java.security.Permission> en = pc.elements();
        while (en.hasMoreElements()) {
            en.nextElement();
            count++;
        }
        check(count == 2, "expected 2 elements but got " + count);

        PermissionCollection other = p.newPermissionCollection();
        check(!other.implies(new PropertyPermission("user.dir", "read")), "collections must be independent");
        check(!other.elements().hasMoreElements(), "new collection must be empty");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
