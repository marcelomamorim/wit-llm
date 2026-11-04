/* @test    @summary Test LinkPermission constructor with valid and invalid parameters    @run main LinkPermissionWitupTest */

import java.nio.file.LinkPermission;

public class LinkPermissionWitupTest {

    public static void main(String[] args) {
        testValidConstructor("hard", null);
        testValidConstructor("symbolic", "");
        testInvalidConstructor("invalid", null);
        testInvalidConstructor("", null);
        testInvalidConstructor("hard", "nonEmptyActions");
    }

    static void testValidConstructor(String name, String actions) {
        try {
            LinkPermission perm = new LinkPermission(name, actions);
            System.out.println("Created LinkPermission with name: " + name + " and actions: " + actions);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for valid input: " + name, e);
        }
    }

    static void testInvalidConstructor(String name, String actions) {
        boolean exceptionThrown = false;
        try {
            new LinkPermission(name, actions);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
            System.out.println("Correctly caught exception for name: " + name + " actions: " + actions);
        }
        if (!exceptionThrown) {
            throw new RuntimeException("Expected IllegalArgumentException for name: " + name + " actions: " + actions);
        }
    }
}
