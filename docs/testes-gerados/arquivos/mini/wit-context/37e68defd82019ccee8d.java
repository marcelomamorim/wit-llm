/*
 * @test
 * @summary Test sun.security.x509.InhibitAnyPolicyExtension.encode(OutputStream) for normal and edge cases
 * @run main InhibitAnyPolicyExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.InhibitAnyPolicyExtension;
import sun.security.x509.PKIXExtensions;

public class InhibitAnyPolicyExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeNormal();
        testEncodeWithPreEncodedValue();
        testEncodeWithNullOutputStream();
    }

    private static void testEncodeNormal() throws IOException {
        // Create instance with a valid skipCerts value
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }
        // Check that extensionId is set to PKIXExtensions.InhibitAnyPolicy_Id
        if (!PKIXExtensions.InhibitAnyPolicy_Id.equals(ext.getExtensionId())) {
            throw new RuntimeException("extensionId not set correctly after encode");
        }
        // Check that critical is true after encode
        if (!ext.isCritical()) {
            throw new RuntimeException("critical flag not set to true after encode");
        }
    }

    private static void testEncodeWithPreEncodedValue() throws IOException {
        // Create instance and force extensionValue to non-null to skip encodeThis
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(1);
        // Manually set extensionValue to simulate pre-encoded value
        // Using reflection is disallowed, so use public API if possible
        // No public setter for extensionValue, so rely on constructor with Object value
        // Use constructor with critical and value to set extensionValue
        byte[] dummyValue = new byte[] {0x05, 0x00}; // DER NULL
        InhibitAnyPolicyExtension ext2 = new InhibitAnyPolicyExtension(Boolean.TRUE, dummyValue);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext2.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty for pre-encoded value");
        }
    }

    private static void testEncodeWithNullOutputStream() {
        try {
            InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
            ext.encode(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected
        } catch (IOException ioe) {
            // IOException is declared but NullPointerException is expected for null stream
            throw new RuntimeException("Unexpected IOException instead of NullPointerException", ioe);
        }
    }
}
