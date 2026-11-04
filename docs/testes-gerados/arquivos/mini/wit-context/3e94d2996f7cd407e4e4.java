/*
 * @test
 * @summary Test PropertyPermission constructor with valid and invalid actions strings
 * @run main PropertyPermissionWitupTest
 */

import java.util.PropertyPermission;

public class PropertyPermissionWitupTest {
    public static void main(String[] args) {
        testValidActions();
        testInvalidActions();
        testNullNameWithValidActions();
    }

    private static void testValidActions() {
        // Valid actions: "read"
        PropertyPermission p1 = new PropertyPermission("java.home", "read");
        if (!"read".equalsIgnoreCase(p1.getActions())) {
            throw new RuntimeException("Expected actions to be 'read'");
        }

        // Valid actions: "write"
        PropertyPermission p2 = new PropertyPermission("java.home", "write");
        if (!"write".equalsIgnoreCase(p2.getActions())) {
            throw new RuntimeException("Expected actions to be 'write'");
        }

        // Valid actions: "read,write" (case insensitive and order)
        PropertyPermission p3 = new PropertyPermission("java.home", "read,write");
        String acts = p3.getActions();
        if (!("read,write".equalsIgnoreCase(acts) || "write,read".equalsIgnoreCase(acts))) {
            throw new RuntimeException("Expected actions to be 'read,write' or 'write,read'");
        }
    }

    private static void testInvalidActions() {
        // Invalid actions string should throw IllegalArgumentException
        try {
            new PropertyPermission("java.home", "invalid");
            throw new RuntimeException("Expected IllegalArgumentException for invalid actions");
        } catch (IllegalArgumentException expected) {
        }

        try {
            new PropertyPermission("java.home", "read,invalid");
            throw new RuntimeException("Expected IllegalArgumentException for mixed invalid actions");
        } catch (IllegalArgumentException expected) {
        }

        try {
            new PropertyPermission("java.home", "");
            throw new RuntimeException("Expected IllegalArgumentException for empty actions");
        } catch (IllegalArgumentException expected) {
        }

        try {
            new PropertyPermission("java.home", null);
            throw new RuntimeException("Expected NullPointerException for null actions");
        } catch (NullPointerException expected) {
        }
    }

    private static void testNullNameWithValidActions() {
        // According to source, name can be null only if mask is not zero, but constructor calls super(name, actions)
        // which throws NullPointerException if name is null. So test that.
        try {
            new PropertyPermission(null, "read");
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
        }
    }
}
