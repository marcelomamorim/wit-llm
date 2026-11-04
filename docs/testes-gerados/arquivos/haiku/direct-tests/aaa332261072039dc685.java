/*
 * @test
 * @summary Checks observable behavior of the BasicPermissionCollection created by BasicPermission.newPermissionCollection()
 * @run main BasicPermissionWitupTest
 */

import java.security.BasicPermission;
import java.security.Permission;
import java.security.PermissionCollection;
import java.util.Enumeration;

public class BasicPermissionWitupTest {

    static class P extends BasicPermission {
        P(String name) {
            super(name);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        PermissionCollection pc = new P("x").newPermissionCollection();
        check(pc != null, "collection must not be null");
        check(!pc.isReadOnly(), "new collection must not be read-only");
        check(!pc.implies(new P("a")), "empty collection must not imply anything");

        pc.add(new P("foo.*"));
        check(pc.implies(new P("foo.bar")), "wildcard foo.* must imply foo.bar");
        check(!pc.implies(new P("bar")), "foo.* must not imply bar");

        pc.add(new P("exact"));
        check(pc.implies(new P("exact")), "exact name must be implied");

        int count = 0;
        Enumeration<Permission> e = pc.elements();
        while (e.hasMoreElements()) {
            e.nextElement();
            count++;
        }
        check(count == 2, "expected 2 elements but got " + count);

        pc.setReadOnly();
        check(pc.isReadOnly(), "collection must be read-only after setReadOnly");
    }
}
