/* @test    @summary Test BasicPermission constructor with valid names    @run main BasicPermissionWitupTest */

import java.security.BasicPermission;

public class BasicPermissionWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidName();
        testConstructorWithEmptyName();
        testConstructorWithNullName();
    }

    static void testConstructorWithValidName() {
        BasicPermission perm = new BasicPermission("testName");
        assert perm != null : "Permission should not be null";
        assert perm.getClass() == BasicPermission.class : "Permission should be of type BasicPermission";
    }

    static void testConstructorWithEmptyName() {
        try {
            new BasicPermission("");
            assert false : "Expected IllegalArgumentException for empty name";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testConstructorWithNullName() {
        try {
            new BasicPermission(null);
            assert false : "Expected NullPointerException for null name";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
