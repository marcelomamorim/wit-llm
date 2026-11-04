/*
 * @test
 * @summary Test LinkPermission(String name, String actions) constructor for valid and invalid inputs
 * @run main LinkPermissionWitupTest
 */

import java.nio.file.LinkPermission;

public class LinkPermissionWitupTest {
    public static void main(String[] args) {
        testValidNames();
        testInvalidName();
        testInvalidActions();
    }

    private static void testValidNames() {
        // Valid names "hard" and "symbolic" with null or empty actions
        new LinkPermission("hard", null);
        new LinkPermission("hard", "");
        new LinkPermission("symbolic", null);
        new LinkPermission("symbolic", "");
    }

    private static void testInvalidName() {
        // Invalid name should throw IllegalArgumentException
        try {
            new LinkPermission("invalidName", null);
            throw new RuntimeException("Expected IllegalArgumentException for invalid name");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    private static void testInvalidActions() {
        // Non-empty actions string should throw IllegalArgumentException
        try {
            new LinkPermission("hard", "read");
            throw new RuntimeException("Expected IllegalArgumentException for non-empty actions");
        } catch (IllegalArgumentException e) {
            // Expected
        }
        try {
            new LinkPermission("symbolic", "write");
            throw new RuntimeException("Expected IllegalArgumentException for non-empty actions");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
