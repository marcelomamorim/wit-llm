/*
 * @test
 * @summary Test BasicPermission(String name, String actions) constructor behavior
 * @run main BasicPermissionWitupTest
 */

import java.security.BasicPermission;

public class BasicPermissionWitupTest extends BasicPermission {
    private static final long serialVersionUID = 1L;

    public BasicPermissionWitupTest(String name) {
        super(name, null);
    }

    public static void main(String[] args) {
        // Test valid name without wildcard
        BasicPermission p1 = new BasicPermissionWitupTest("read");
        if (!"read".equals(p1.getName())) {
            throw new RuntimeException("Expected name 'read', got: " + p1.getName());
        }

        // Test valid name with wildcard "*"
        BasicPermission p2 = new BasicPermissionWitupTest("*");
        if (!"*".equals(p2.getName())) {
            throw new RuntimeException("Expected name '*', got: " + p2.getName());
        }

        // Test valid name with wildcard suffix ".*"
        BasicPermission p3 = new BasicPermissionWitupTest("java.*");
        if (!"java.*".equals(p3.getName())) {
            throw new RuntimeException("Expected name 'java.*', got: " + p3.getName());
        }

        // Test special case "exitVM"
        BasicPermission p4 = new BasicPermissionWitupTest("exitVM");
        if (!"exitVM".equals(p4.getName())) {
            throw new RuntimeException("Expected name 'exitVM', got: " + p4.getName());
        }

        // Test null name throws NullPointerException
        try {
            new BasicPermissionWitupTest(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            // expected
        }

        // Test empty string name throws IllegalArgumentException
        try {
            new BasicPermissionWitupTest("");
            throw new RuntimeException("Expected IllegalArgumentException for empty name");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test invalid wildcard usage throws IllegalArgumentException
        try {
            new BasicPermissionWitupTest("a*b");
            throw new RuntimeException("Expected IllegalArgumentException for invalid wildcard usage");
        } catch (IllegalArgumentException e) {
            // expected
        }

        System.out.println("BasicPermission constructor tests passed.");
    }
}
