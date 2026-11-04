/*
 * @test
 * @summary Test sun.security.x509.CRLReasonCodeExtension.encode(OutputStream) method
 * @run main CRLReasonCodeExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.CRLReasonCodeExtension;
import sun.security.x509.PKIXExtensions;

public class CRLReasonCodeExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithNullExtensionValue();
        testEncodeWithNonNullExtensionValue();
    }

    private static void testEncodeWithNullExtensionValue() throws IOException {
        // Create instance with reasonCode 0 so extensionValue is null
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(false, 0);
        // extensionValue should be null internally
        // encode should set extensionId and critical to expected values and produce output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.extensionValue = null; // forcibly null to test encode path
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        // encoded output should not be empty
        if (encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty when extensionValue is null");
        }
        // extensionId should be ReasonCode_Id
        if (!ext.extensionId.equals(PKIXExtensions.ReasonCode_Id)) {
            throw new RuntimeException("extensionId not set to ReasonCode_Id");
        }
        // critical should be false
        if (ext.critical != false) {
            throw new RuntimeException("critical flag not false when extensionValue is null");
        }
    }

    private static void testEncodeWithNonNullExtensionValue() throws IOException {
        // Create instance with reasonCode 1 (non-zero) so extensionValue is non-null
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(false, 1);
        // extensionValue should be non-null
        if (ext.extensionValue == null) {
            throw new RuntimeException("extensionValue is null for non-zero reasonCode");
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        // encoded output should not be empty
        if (encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty when extensionValue is non-null");
        }
        // extensionId should be ReasonCode_Id
        if (!ext.extensionId.equals(PKIXExtensions.ReasonCode_Id)) {
            throw new RuntimeException("extensionId not set to ReasonCode_Id");
        }
        // critical should be false as set in constructor
        if (ext.critical != false) {
            throw new RuntimeException("critical flag not false for non-zero reasonCode");
        }
    }
}
