/* @test    @summary Test constructor of PrivateCredentialPermission with valid and invalid actions    @run main PrivateCredentialPermissionWitupTest */

import javax.security.auth.PrivateCredentialPermission;

public class PrivateCredentialPermissionWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testInvalidActions();
    }

    static void testValidConstructor() {
        try {
            PrivateCredentialPermission perm = new PrivateCredentialPermission("com.sun.PrivateCredential", "read");
            System.out.println("testValidConstructor: PASSED");
        } catch (Exception e) {
            System.out.println("testValidConstructor: FAILED with exception: " + e);
        }
    }

    static void testInvalidActions() {
        try {
            new PrivateCredentialPermission("com.sun.PrivateCredential", "write");
            System.out.println("testInvalidActions: FAILED - exception expected");
        } catch (IllegalArgumentException e) {
            System.out.println("testInvalidActions: PASSED");
        } catch (Exception e) {
            System.out.println("testInvalidActions: FAILED with unexpected exception: " + e);
        }
    }
}
