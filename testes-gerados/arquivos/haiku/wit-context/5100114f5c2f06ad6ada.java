/*
 * @test
 * @summary Verifies that SecurityManager.checkPackageDefinition rejects a null package name with a NullPointerException
 * @run main SecurityManagerWitupTest
 */

public class SecurityManagerWitupTest {

    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkPackageDefinition(null);
        } catch (NullPointerException e) {
            if (!"package name can't be null".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("expected NullPointerException for null package name");
    }
}
