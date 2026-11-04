/*
 * @test
 * @summary Observable behavior of SecurityManager.checkPackageAccess for null, unrestricted and boundary package names
 * @run main SecurityManagerWitupTest
 */

public class SecurityManagerWitupTest {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();

        boolean npeThrown = false;
        try {
            sm.checkPackageAccess(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException for null package name");
        }

        sm.checkPackageAccess("com.example.unrestricted");

        sm.checkPackageAccess("sunny.pkg");

        try {
            sm.checkPackageAccess("sun.misc");
        } catch (SecurityException e) {
        }
    }
}
