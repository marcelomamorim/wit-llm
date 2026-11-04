/*
 * @test
 * @summary Test sun.security.x509.AuthorityKeyIdentifierExtension.encode(OutputStream) for expected behavior and exception paths
 * @run main AuthorityKeyIdentifierExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.AuthorityKeyIdentifierExtension;
import sun.security.x509.PKIXExtensions;

public class AuthorityKeyIdentifierExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithNullExtensionValue();
        testEncodeWithNonNullExtensionValueAndNullExtensionId();
        testEncodeWithNonNullExtensionValueAndNonNullExtensionIdButNullExtensionValueField();
    }

    // Test normal encode path when extensionValue is initially null
    private static void testEncodeWithNullExtensionValue() throws IOException {
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension(Boolean.FALSE, new byte[] {1, 2, 3});
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoded output should not be empty when extensionValue is initially null");
        }
        // Check that extensionId was set to PKIXExtensions.AuthorityKey_Id
        if (!PKIXExtensions.AuthorityKey_Id.equals(ext.extensionId)) {
            throw new AssertionError("extensionId should be set to PKIXExtensions.AuthorityKey_Id");
        }
        // Check that critical is false
        if (ext.critical) {
            throw new AssertionError("critical should be false after encode when extensionValue was null");
        }
    }

    // Test encode when extensionValue is non-null but extensionId is null, expecting IOException
    private static void testEncodeWithNonNullExtensionValueAndNullExtensionId() {
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension(Boolean.FALSE, new byte[] {1, 2, 3});
        // Force extensionValue non-null
        ext.extensionValue = new byte[] {1, 2, 3};
        // Force extensionId null to trigger exception path
        ext.extensionId = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ext.encode(baos);
            throw new AssertionError("Expected IOException due to null extensionId when extensionValue is non-null");
        } catch (IOException expected) {
            // Expected exception
        }
    }

    // Test encode when extensionValue is non-null, extensionId non-null, but extensionValue field is null, expecting IOException
    private static void testEncodeWithNonNullExtensionValueAndNonNullExtensionIdButNullExtensionValueField() {
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension(Boolean.FALSE, new byte[] {1, 2, 3});
        // Force extensionValue non-null
        ext.extensionValue = new byte[] {1, 2, 3};
        // Set extensionId to valid value
        ext.extensionId = PKIXExtensions.AuthorityKey_Id;
        // Set extensionValue field to null to trigger exception path
        ext.extensionValue = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ext.encode(baos);
            throw new AssertionError("Expected IOException due to null extensionValue field when extensionId is non-null and extensionValue is non-null");
        } catch (IOException expected) {
            // Expected exception
        }
    }
}