/* @test    @summary Test constructor of LinkPermission with valid and invalid names    @run main LinkPermissionWitupTest */

import java.nio.file.LinkPermission;

public class LinkPermissionWitupTest {

    public static void main(String[] args) {
        testValidNameHard();
        testValidNameSymbolic();
        testInvalidName();
    }

    static void testValidNameHard() {
        LinkPermission perm = new LinkPermission("hard");
        assert perm != null : "Failed to create LinkPermission with name 'hard'";
    }

    static void testValidNameSymbolic() {
        LinkPermission perm = new LinkPermission("symbolic");
        assert perm != null : "Failed to create LinkPermission with name 'symbolic'";
    }

    static void testInvalidName() {
        boolean exceptionThrown = false;
        try {
            new LinkPermission("invalid");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for invalid name";
    }
}
