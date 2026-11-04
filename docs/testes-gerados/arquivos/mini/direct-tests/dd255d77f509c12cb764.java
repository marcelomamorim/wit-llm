/*
 * @test
 * @summary Test LinkPermission(String name, String actions) constructor behavior
 * @run main LinkPermissionWitupTest
 */

import java.nio.file.LinkPermission;

public class LinkPermissionWitupTest {
    public static void main(String[] args) {
        testValidNameNullActions();
        testValidNameEmptyActions();
        testInvalidName();
        testNonEmptyActions();
    }

    private static void testValidNameNullActions() {
        LinkPermission p1 = new LinkPermission("hard", null);
        if (!"hard".equals(p1.getName())) {
            throw new RuntimeException("Expected name 'hard', got: " + p1.getName());
        }
        LinkPermission p2 = new LinkPermission("symbolic", null);
        if (!"symbolic".equals(p2.getName())) {
            throw new RuntimeException("Expected name 'symbolic', got: " + p2.getName());
        }
    }

    private static void testValidNameEmptyActions() {
        LinkPermission p1 = new LinkPermission("hard", "");
        if (!"hard".equals(p1.getName())) {
            throw new RuntimeException("Expected name 'hard', got: " + p1.getName());
        }
        LinkPermission p2 = new LinkPermission("symbolic", "");
        if (!"symbolic".equals(p2.getName())) {
            throw new RuntimeException("Expected name 'symbolic', got: " + p2.getName());
        }
    }

    private static void testInvalidName() {
        try {
            new LinkPermission("invalid", null);
            throw new RuntimeException("Expected IllegalArgumentException for invalid name");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("name: invalid")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testNonEmptyActions() {
        try {
            new LinkPermission("hard", "read");
            throw new RuntimeException("Expected IllegalArgumentException for non-empty actions");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("actions: read")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
