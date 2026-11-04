/*
 * @test
 * @summary Test constructor of java.security.Permissions
 * @run main PermissionsWitupTest
 */

import java.security.Permissions;

public class PermissionsWitupTest {
    public static void main(String[] args) {
        Permissions perms = new Permissions();
        if (perms == null) {
            throw new RuntimeException("Permissions constructor returned null");
        }
        // The constructor initializes permsMap and allPermission to null
        // but these fields are private and transient, no direct access.
        // We test that the object is non-null and can be used.
        // Since no public methods are given, just ensure no exception on construction.
        System.out.println("Permissions instance created successfully.");
    }
}
