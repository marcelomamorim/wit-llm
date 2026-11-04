/* @test    @summary Test for BasicPermission constructor handling null and empty name    @run main BasicPermissionWitupTest */

import java.security.BasicPermission;

public class BasicPermissionWitupTest {

    public static void main(String[] args) {
        testNullName();
        testEmptyName();
    }

    private static void testNullName() {
        try {
            new BasicPermission(null, "actions");
            System.err.println("Failed: Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // Test passes
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for null name: " + t);
        }
    }

    private static void testEmptyName() {
        try {
            new BasicPermission("", "actions");
            System.err.println("Failed: Expected IllegalArgumentException for empty name");
        } catch (IllegalArgumentException expected) {
            // Test passes
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for empty name: " + t);
        }
    }
}
