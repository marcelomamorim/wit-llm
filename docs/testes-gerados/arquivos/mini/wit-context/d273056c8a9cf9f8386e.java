/*
 * @test
 * @summary Test sun.security.x509.PrivateKeyUsageExtension.encode(OutputStream) for normal and edge cases
 * @run main PrivateKeyUsageExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import sun.security.x509.PrivateKeyUsageExtension;

public class PrivateKeyUsageExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithNullExtensionValue();
        testEncodeWithSetDates();
    }

    private static void testEncodeWithNullExtensionValue() throws IOException {
        // Create instance with notBefore and notAfter null, so extensionValue is null initially
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension((Date) null, (Date) null);

        // Use ByteArrayOutputStream to capture output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Call encode - should set extensionId and critical, encodeThis and write output
        ext.encode(baos);

        byte[] encoded = baos.toByteArray();

        // The encoded output should not be empty (it encodes the extension structure)
        if (encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty when it should contain extension encoding");
        }
    }

    private static void testEncodeWithSetDates() throws IOException {
        // Set notBefore and notAfter dates
        Date notBefore = new Date(1000000000000L); // Sat Sep 08 2001
        Date notAfter = new Date(2000000000000L);  // Sun May 18 2033

        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(notBefore, notAfter);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ext.encode(baos);

        byte[] encoded = baos.toByteArray();

        if (encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty despite valid notBefore and notAfter dates");
        }
    }
}
