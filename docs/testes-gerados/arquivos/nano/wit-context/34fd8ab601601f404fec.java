/* @test    @summary Test for BasicPermission constructor with null and empty name    @run main BasicPermissionWitupTest */

import java.security.BasicPermission;

public class BasicPermissionWitupTest {

    public static void main(String[] args) {
        testNullName();
        testEmptyName();
    }

    private static void testNullName() {
        try {
            new BasicPermission(null);
            System.err.println("Failed: Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type for null name: " + t);
        }
    }

    private static void testEmptyName() {
        try {
            new BasicPermission("");
            System.err.println("Failed: Expected IllegalArgumentException for empty name");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type for empty name: " + t);
        }
    }
}
