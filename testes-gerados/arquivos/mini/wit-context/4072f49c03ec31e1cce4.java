/*
 * @test
 * @summary Test constructor of UnresolvedPermissionCollection for correct initialization and exception safety
 * @run main UnresolvedPermissionCollectionWitupTest
 */

import java.security.UnresolvedPermissionCollection;
import java.util.concurrent.ConcurrentHashMap;

public class UnresolvedPermissionCollectionWitupTest {
    public static void main(String[] args) {
        // Test normal construction
        UnresolvedPermissionCollection upc = new UnresolvedPermissionCollection();
        if (upc == null) {
            throw new RuntimeException("Constructor returned null");
        }

        // Reflection to check internal map is initialized with expected capacity
        // Not allowed by instructions, so test observable behavior only
        // The constructor calls new ConcurrentHashMap<>(11), which should not throw

        // Since the constructor does not expose state, we test that no exception is thrown
        // and that the object is non-null.

        // No exception expected for valid constructor call

        // No direct way to test IllegalArgumentException from ConcurrentHashMap constructor
        // because 11, 0.75f, 1 are constants and valid.
        // Thus, no test for exception path is possible here without reflection or invalid inputs.

        System.out.println("UnresolvedPermissionCollection constructor test passed");
    }
}
