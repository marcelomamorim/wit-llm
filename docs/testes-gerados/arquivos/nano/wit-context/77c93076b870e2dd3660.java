/* @test    @summary Test for PrivateCredentialPermission constructor with valid and invalid actions    @run main PrivateCredentialPermissionWitupTest */

import javax.security.auth.PrivateCredentialPermission;

public class PrivateCredentialPermissionWitupTest {

    public static void main(String[] args) {
        testValidActions();
        testInvalidActions();
    }

    private static void testValidActions() {
        try {
            PrivateCredentialPermission pcp = new PrivateCredentialPermission("SomeName", "read");
            System.out.println("testValidActions passed");
        } catch (Exception e) {
            System.err.println("testValidActions failed: " + e);
        }
    }

    private static void testInvalidActions() {
        try {
            PrivateCredentialPermission pcp = new PrivateCredentialPermission("SomeName", "write");
            System.err.println("testInvalidActions failed: no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testInvalidActions passed");
        } catch (Exception e) {
            System.err.println("testInvalidActions failed: " + e);
        }
    }
}
