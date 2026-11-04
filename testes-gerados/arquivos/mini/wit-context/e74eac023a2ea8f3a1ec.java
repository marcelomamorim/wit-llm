/*
 * @test
 * @summary Test sun.security.x509.CRLReasonCodeExtension.encode(OutputStream) method
 * @run main CRLReasonCodeExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.CRLReasonCodeExtension;

public class CRLReasonCodeExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithNullExtensionValue();
        testEncodeWithNonNullExtensionValue();
    }

    private static void testEncodeWithNullExtensionValue() throws IOException {
        // Create instance with reasonCode != 0 to force encodeThis() to set extensionValue
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(false, 1);
        // extensionValue should be set by constructor
        if (ext.extensionValue == null) {
            throw new RuntimeException("extensionValue should not be null after construction with reason != 0");
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output should not be empty");
        }
    }

    private static void testEncodeWithNonNullExtensionValue() throws IOException {
        // Create instance with reasonCode == 0, so extensionValue is null initially
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(false, 0);
        // Manually set extensionValue to a valid DER encoding to simulate non-null
        ext.extensionValue = new byte[] {0x0a, 0x01, 0x00}; // arbitrary valid DER
        ext.extensionId = sun.security.x509.PKIXExtensions.ReasonCode_Id;
        ext.critical = true;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output should not be empty when extensionValue is set");
        }
    }
}