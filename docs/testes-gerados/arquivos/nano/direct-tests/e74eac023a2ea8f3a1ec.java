/* @test    @summary Test encode method of CRLReasonCodeExtension for correct output behavior    @run main CRLReasonCodeExtensionWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.CRLReasonCodeExtension;

public class CRLReasonCodeExtensionWitupTest {

    public static void main(String[] args) throws IOException {
        testEncodeWithReason();
        testEncodeWithNullExtensionValue();
    }

    private static void testEncodeWithReason() throws IOException {
        int reason = 1; // Example reason code
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(reason);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] result = baos.toByteArray();
        if (result == null || result.length == 0) {
            throw new AssertionError("Encoding with reason failed: output is null or empty");
        }
        boolean containsReasonCode = false;
        for (byte b : result) {
            if (b == (byte) reason) {
                containsReasonCode = true;
                break;
            }
        }
        if (!containsReasonCode) {
            throw new AssertionError("Encoded output does not contain the reason code");
        }
    }

    private static void testEncodeWithNullExtensionValue() throws IOException {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(0); // reason 0, which sets extensionValue to null
        ext.extensionValue = null; // forcibly set to null to test encode behavior
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] result = baos.toByteArray();
        if (result == null || result.length == 0) {
            throw new AssertionError("Encoding with null extensionValue failed: output is null or empty");
        }
    }
}
