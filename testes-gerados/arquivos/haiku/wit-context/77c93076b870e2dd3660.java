/*
 * @test
 * @summary Witup-generated tests for PrivateCredentialPermission constructor actions validation
 * @run main PrivateCredentialPermissionWitupTest
 */

import javax.security.auth.PrivateCredentialPermission;

public class PrivateCredentialPermissionWitupTest {
    private static final String NAME = "com.sun.PrivateCredential com.sun.Principal \"duke\"";
    private static int failures = 0;

    public static void main(String[] args) {
        testAcceptsReadActions();
        testRejectsInvalidActions();
        if (failures > 0) {
            throw new RuntimeException(failures + " failure(s)");
        }
    }

    static void testAcceptsReadActions() {
        String[] valid = {"read", "READ", "Read"};
        for (String a : valid) {
            try {
                new PrivateCredentialPermission(NAME, a);
            } catch (RuntimeException e) {
                fail("unexpected exception for actions " + a + ": " + e);
            }
        }
    }

    static void testRejectsInvalidActions() {
        String[] invalid = {"write", "", "reads", null};
        for (String a : invalid) {
            try {
                new PrivateCredentialPermission(NAME, a);
                fail("expected IllegalArgumentException for actions " + a);
            } catch (IllegalArgumentException expected) {
            }
        }
    }

    static void fail(String msg) {
        failures++;
        System.err.println(msg);
    }
}
