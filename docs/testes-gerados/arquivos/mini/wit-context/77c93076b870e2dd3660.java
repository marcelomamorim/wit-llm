/*
 * @test
 * @summary Test PrivateCredentialPermission constructor with valid and invalid actions
 * @run main javax.security.auth.PrivateCredentialPermissionWitupTest
 */

import javax.security.auth.PrivateCredentialPermission;

public class PrivateCredentialPermissionWitupTest {
    public static void main(String[] args) {
        testValidAction();
        testInvalidAction();
        System.out.println("All tests passed.");
    }

    private static void testValidAction() {
        // Valid action "read" (case insensitive)
        PrivateCredentialPermission p1 = new PrivateCredentialPermission("a.b.Credential a.b.Principal \"duke\"", "read");
        PrivateCredentialPermission p2 = new PrivateCredentialPermission("a.b.Credential a.b.Principal \"duke\"", "READ");
        PrivateCredentialPermission p3 = new PrivateCredentialPermission("a.b.Credential a.b.Principal \"duke\"", "ReAd");
        if (p1 == null || p2 == null || p3 == null) {
            throw new RuntimeException("Constructor returned null unexpectedly");
        }
    }

    private static void testInvalidAction() {
        // Invalid action should throw IllegalArgumentException
        boolean thrown = false;
        try {
            new PrivateCredentialPermission("a.b.Credential a.b.Principal \"duke\"", "write");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for invalid action");
        }

        thrown = false;
        try {
            new PrivateCredentialPermission("a.b.Credential a.b.Principal \"duke\"", "");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for empty action");
        }

        thrown = false;
        try {
            new PrivateCredentialPermission("a.b.Credential a.b.Principal \"duke\"", null);
        } catch (NullPointerException e) {
            // The constructor does not explicitly check for null, so NPE may be thrown by super(name) or init(name)
            thrown = true;
        } catch (IllegalArgumentException e) {
            // Defensive: if IllegalArgumentException is thrown instead, accept it
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected exception for null action");
        }
    }
}
