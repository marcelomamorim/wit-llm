/*
 * @test
 * @summary Test sun.security.x509.InhibitAnyPolicyExtension.encode(OutputStream) method
 * @run main InhibitAnyPolicyExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.InhibitAnyPolicyExtension;
import sun.security.x509.PKIXExtensions;

public class InhibitAnyPolicyExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithNullExtensionValue();
        testEncodeWithPredefinedExtensionValue();
    }

    private static void testEncodeWithNullExtensionValue() throws IOException {
        // Create instance with skipCerts = 0 (valid)
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
        // extensionValue should be null initially
        ext.extensionValue = null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        // The encoded output should not be empty
        if (encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty when extensionValue was null");
        }

        // The extensionId should be set to PKIXExtensions.InhibitAnyPolicy_Id
        if (!ext.extensionId.equals(PKIXExtensions.InhibitAnyPolicy_Id)) {
            throw new RuntimeException("extensionId not set to PKIXExtensions.InhibitAnyPolicy_Id");
        }

        // The critical flag should be true
        if (!ext.critical) {
            throw new RuntimeException("critical flag not set to true");
        }
    }

    private static void testEncodeWithPredefinedExtensionValue() throws IOException {
        // Create instance with skipCerts = 1
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(1);
        // Manually set extensionValue to a non-null dummy value
        ext.extensionValue = new byte[] {1, 2, 3};

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        // The encoded output should not be empty
        if (encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty when extensionValue was preset");
        }

        // The extensionId should remain null (not set by encodeThis)
        if (ext.extensionId != null) {
            throw new RuntimeException("extensionId should not be set when extensionValue is preset");
        }
    }
}
