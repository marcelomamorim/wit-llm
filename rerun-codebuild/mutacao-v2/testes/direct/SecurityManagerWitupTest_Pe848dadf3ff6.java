/*
 * @test
 * @summary Test SecurityManager.checkLink(String) for null argument and permission check
 * @run main SecurityManagerWitupTest_Pe848dadf3ff6
 */

public class SecurityManagerWitupTest_Pe848dadf3ff6 {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();

        // Test that passing null throws NullPointerException
        try {
            sm.checkLink(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            if (!"library can't be null".equals(npe.getMessage())) {
                throw new Error("Unexpected NullPointerException message: " + npe.getMessage());
            }
        }

        // Test that passing a non-null string calls checkPermission with correct RuntimePermission
        // Since default SecurityManager.checkPermission calls AccessController.checkPermission,
        // and no SecurityManager is installed, this should pass silently.
        sm.checkLink("testlib");

        // Test with empty string
        sm.checkLink("");
    }
}
