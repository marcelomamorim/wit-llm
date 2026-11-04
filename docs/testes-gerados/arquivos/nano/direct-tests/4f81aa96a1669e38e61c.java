/* @test    @summary Test constructor BasicPermission(String, String) for BasicPermission class    @run main BasicPermissionWitupTest */

import java.security.BasicPermission;

public class BasicPermissionWitupTest {

    public static void main(String[] args) {
        testValidName();
        testNullName();
        testEmptyName();
    }

    static void testValidName() {
        BasicPermission perm = new BasicPermission("test.permission", "actions");
        assert perm != null : "Permission should not be null";
        assert perm instanceof BasicPermission : "Permission should be instance of BasicPermission";
    }

    static void testNullName() {
        try {
            new BasicPermission(null, "actions");
            assert false : "Expected NullPointerException for null name";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testEmptyName() {
        try {
            new BasicPermission("", "actions");
            assert false : "Expected IllegalArgumentException for empty name";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
