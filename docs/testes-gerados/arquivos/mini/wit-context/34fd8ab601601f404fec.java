/*
 * @test
 * @summary Test BasicPermission(String name) constructor for null and empty name inputs
 * @run main BasicPermissionWitupTest
 */

import java.security.BasicPermission;

public class BasicPermissionWitupTest extends BasicPermission {

    private static final long serialVersionUID = 1L;

    // Constructor forwarding to super
    public BasicPermissionWitupTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        // Test null name triggers NullPointerException
        try {
            new BasicPermissionWitupTest(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test empty string name triggers IllegalArgumentException
        try {
            new BasicPermissionWitupTest("");
            throw new RuntimeException("Expected IllegalArgumentException for empty name");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test valid name does not throw
        BasicPermissionWitupTest p = new BasicPermissionWitupTest("validName");
        if (!"validName".equals(p.getName())) {
            throw new RuntimeException("Name not set correctly");
        }

        System.out.println("BasicPermissionWitupTest passed");
    }
}
