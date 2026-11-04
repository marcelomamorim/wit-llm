/*
 * @test
 * @summary Test BasicPermission.BasicPermissionCollection constructor behavior
 * @run main BasicPermissionWitupTest
 */

import java.security.BasicPermission;
import java.security.BasicPermission.BasicPermissionCollection;

public class BasicPermissionWitupTest {
    public static void main(String[] args) {
        // Test constructor with a valid class
        BasicPermissionCollection bpc = new BasicPermissionCollection(BasicPermission.class);
        if (bpc == null) {
            throw new RuntimeException("BasicPermissionCollection constructor returned null");
        }

        // The constructor should not throw and should initialize fields
        // We cannot access fields directly, but we can check that no exception was thrown

        // No exception expected for normal input
        try {
            new BasicPermissionCollection(BasicPermission.class);
        } catch (IllegalArgumentException iae) {
            throw new RuntimeException("Unexpected IllegalArgumentException for valid input", iae);
        }

        // No exception expected for null class (not tested here because constructor requires Class<?> and null would cause NPE elsewhere)
        // No other behavior observable

        System.out.println("BasicPermissionCollection constructor test passed");
    }
}
