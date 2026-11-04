/*
 * @test
 * @summary Test constructor of PrivateCredentialPermission with valid and invalid actions
 * @run main javax.security.auth.PrivateCredentialPermissionWitupTest
 */

public class PrivateCredentialPermissionWitupTest {
    public static void main(String[] args) {
        testValidAction();
        testInvalidAction();
    }

    private static void testValidAction() {
        // Should succeed with "read" (case insensitive)
        new javax.security.auth.PrivateCredentialPermission("a.b.Credential a.b.Principal \"duke\"", "read");
        new javax.security.auth.PrivateCredentialPermission("a.b.Credential a.b.Principal \"duke\"", "READ");
        new javax.security.auth.PrivateCredentialPermission("a.b.Credential a.b.Principal \"duke\"", "ReAd");
    }

    private static void testInvalidAction() {
        try {
            new javax.security.auth.PrivateCredentialPermission("a.b.Credential a.b.Principal \"duke\"", "write");
            throw new RuntimeException("Expected IllegalArgumentException for invalid action");
        } catch (IllegalArgumentException e) {
            // Expected
        }
        try {
            new javax.security.auth.PrivateCredentialPermission("a.b.Credential a.b.Principal \"duke\"", "");
            throw new RuntimeException("Expected IllegalArgumentException for empty action");
        } catch (IllegalArgumentException e) {
            // Expected
        }
        try {
            new javax.security.auth.PrivateCredentialPermission("a.b.Credential a.b.Principal \"duke\"", null);
            throw new RuntimeException("Expected IllegalArgumentException for null action");
        } catch (NullPointerException | IllegalArgumentException e) {
            // Accept either NullPointerException or IllegalArgumentException depending on implementation
        }
    }
}
