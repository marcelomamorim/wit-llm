/*
 * @test
 * @summary Test sun.security.x509.CRLNumberExtension.encode(OutputStream) for normal and edge cases
 * @run main CRLNumberExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;
import sun.security.x509.PKIXExtensions;

public class CRLNumberExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithValidOutputStream();
        testEncodeWithNullOutputStream();
        testEncodeWithLargeNumber();
    }

    private static void testEncodeWithValidOutputStream() throws IOException {
        // Create CRLNumberExtension with a small integer
        CRLNumberExtension ext = new CRLNumberExtension(12345);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("encode() produced empty output");
        }
    }

    private static void testEncodeWithNullOutputStream() {
        try {
            CRLNumberExtension ext = new CRLNumberExtension(1);
            ext.encode(null);
            throw new RuntimeException("encode() did not throw NullPointerException when passed null OutputStream");
        } catch (NullPointerException e) {
            // Expected, as OutputStream is null
        } catch (IOException e) {
            // IOException is allowed but unexpected here
            throw new RuntimeException("Unexpected IOException when passing null OutputStream", e);
        }
    }

    private static void testEncodeWithLargeNumber() throws IOException {
        // Use a large BigInteger to test encode does not fail
        BigInteger bigNum = new BigInteger("123456789012345678901234567890");
        CRLNumberExtension ext = new CRLNumberExtension(bigNum);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("encode() produced empty output for large BigInteger");
        }
    }
}
