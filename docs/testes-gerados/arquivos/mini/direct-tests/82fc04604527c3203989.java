/*
 * @test
 * @summary Test LinkPermission(String name) constructor for valid and invalid names
 * @run main LinkPermissionWitupTest
 */

import java.nio.file.LinkPermission;

public class LinkPermissionWitupTest {
    public static void main(String[] args) {
        testValidNames();
        testInvalidNames();
    }

    private static void testValidNames() {
        // Valid names "hard" and "symbolic" should construct without exception
        new LinkPermission("hard");
        new LinkPermission("symbolic");
    }

    private static void testInvalidNames() {
        // Invalid names should throw IllegalArgumentException
        String[] invalidNames = {"", "hardlink", "symbol", "HARD", "SYMBOLIC", "hard ", " symbolic"};
        for (String name : invalidNames) {
            try {
                new LinkPermission(name);
                throw new RuntimeException("Expected IllegalArgumentException for name: " + name);
            } catch (IllegalArgumentException e) {
                // Expected
            }
        }
    }
}
