/*
 * @test
 * @summary Test LinkPermission(String name) constructor for valid and invalid names
 * @run main LinkPermissionWitupTest
 */

import java.nio.file.LinkPermission;

public class LinkPermissionWitupTest {
    public static void main(String[] args) {
        testValidNameHard();
        testValidNameSymbolic();
        testInvalidNameThrows();
    }

    private static void testValidNameHard() {
        LinkPermission p = new LinkPermission("hard");
        if (!"hard".equals(p.getName())) {
            throw new AssertionError("Expected name 'hard', got: " + p.getName());
        }
    }

    private static void testValidNameSymbolic() {
        LinkPermission p = new LinkPermission("symbolic");
        if (!"symbolic".equals(p.getName())) {
            throw new AssertionError("Expected name 'symbolic', got: " + p.getName());
        }
    }

    private static void testInvalidNameThrows() {
        try {
            new LinkPermission("invalid");
            throw new AssertionError("Expected IllegalArgumentException for invalid name");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        try {
            new LinkPermission("");
            throw new AssertionError("Expected IllegalArgumentException for empty name");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        try {
            new LinkPermission(null);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
