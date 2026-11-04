/*
 * @test
 * @summary Verifies BasicPermissionCollection created via BasicPermission.newPermissionCollection() stores and implies named permissions
 * @run main BasicPermissionWitupTest
 */

import java.security.BasicPermission;
import java.security.PermissionCollection;
import java.util.Enumeration;

public class BasicPermissionWitupTest {

    static class TestPerm extends BasicPermission {
        private static final long serialVersionUID = 1L;

        TestPerm(String name) {
            super(name);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        PermissionCollection pc = new TestPerm("x.y").newPermissionCollection();
        check(pc != null, "newPermissionCollection returned null");
        check(!pc.isReadOnly(), "new collection should not be read-only");
        check(!pc.elements().hasMoreElements(), "new collection should be empty");
        check(!pc.implies(new TestPerm("a.b")), "empty collection should not imply a.b");

        pc.add(new TestPerm("a.b"));
        check(pc.implies(new TestPerm("a.b")), "exact name should be implied");
        check(!pc.implies(new TestPerm("a.c")), "different name should not be implied");

        Enumeration<java.security.Permission> en = pc.elements();
        check(en.hasMoreElements(), "collection should contain added permission");
        en.nextElement();
        check(!en.hasMoreElements(), "collection should contain exactly one permission");

        PermissionCollection wild = new TestPerm("x.y").newPermissionCollection();
        wild.add(new TestPerm("a.*"));
        check(wild.implies(new TestPerm("a.c")), "wildcard a.* should imply a.c");
        check(wild.implies(new TestPerm("a.b.c")), "wildcard a.* should imply a.b.c");
        check(!wild.implies(new TestPerm("b.c")), "wildcard a.* should not imply b.c");
    }
}
