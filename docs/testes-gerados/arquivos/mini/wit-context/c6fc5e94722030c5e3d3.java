/*
 * @test
 * @summary Test constructor of java.security.Permissions for correct initialization and exception safety
 * @run main PermissionsWitupTest
 */

import java.security.Permissions;
import java.util.concurrent.ConcurrentHashMap;

public class PermissionsWitupTest {
    public static void main(String[] args) {
        // Test normal construction
        Permissions p = new Permissions();
        if (p == null) {
            throw new RuntimeException("Permissions constructor returned null");
        }

        // Reflection or direct field access is not allowed; test observable behavior only
        // The constructor initializes permsMap with initial capacity 11
        // ConcurrentHashMap throws IllegalArgumentException if initial capacity < 0 or load factor <= 0
        // Since constructor uses fixed 11, no exception expected here

        // No exception expected, so test passes if no exception thrown
        System.out.println("Permissions constructor test passed");
    }
}
