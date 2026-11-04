/*
 * @test
 * @summary Verifies the PrivateCredentialPermission constructor accepts only the "read" action (case-insensitive) and rejects other actions with IllegalArgumentException
 * @run main PrivateCredentialPermissionWitupTest
 */

import javax.security.auth.PrivateCredentialPermission;

public class PrivateCredentialPermissionWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + message);
        }
    }

    static void expectIllegalArgument(String name, String actions, String label) {
        try {
            new PrivateCredentialPermission(name, actions);
            check(false, label + ": expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        String name = "a.b.Credential a.b.Principal \"duke\"";

        PrivateCredentialPermission p = new PrivateCredentialPermission(name, "read");
        check(name.equals(p.getName()), "getName should return the constructor name");

        new PrivateCredentialPermission(name, "READ");
        new PrivateCredentialPermission(name, "Read");

        expectIllegalArgument(name, "write", "write action");
        expectIllegalArgument(name, "", "empty action");
        expectIllegalArgument(name, null, "null action");
        expectIllegalArgument(name, "readwrite", "readwrite action");
        expectIllegalArgument(name, "read ", "read with trailing space");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
