/*
 * @test
 * @summary Verifies checkPackageAccess rejects a null package name with NullPointerException and accepts an unrestricted package.
 * @run main SecurityManagerWitupTest
 */

public class SecurityManagerWitupTest {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkPackageAccess(null);
            throw new RuntimeException("NullPointerException expected for null package");
        } catch (NullPointerException e) {
            if (!"package name can't be null".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
        sm.checkPackageAccess("java.lang");
    }
}
