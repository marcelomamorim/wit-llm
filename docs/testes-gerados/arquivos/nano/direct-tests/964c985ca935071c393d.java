/* @test    @summary Test encoding of CRLNumberExtension.    @run main CRLNumberExtensionWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithValidBigInteger();
        testEncodeWithNullCrlNumber();
    }

    static void testEncodeWithValidBigInteger() throws IOException {
        BigInteger crlNum = BigInteger.valueOf(12345);
        CRLNumberExtension ext = new CRLNumberExtension(crlNum);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoding returned null or empty byte array");
        }
        // Basic check: the encoded array should contain the DER encoding of the integer
        boolean containsIntegerTag = false;
        for (byte b : encoded) {
            if (b == 0x02) { // DER INTEGER tag
                containsIntegerTag = true;
                break;
            }
        }
        if (!containsIntegerTag) {
            throw new AssertionError("Encoded data does not contain DER INTEGER tag");
        }
    }

    static void testEncodeWithNullCrlNumber() throws IOException {
        CRLNumberExtension ext = new CRLNumberExtension(BigInteger.ZERO);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoding with zero CRL number returned null or empty byte array");
        }
        // Check that the encoding contains the DER INTEGER tag
        boolean containsIntegerTag = false;
        for (byte b : encoded) {
            if (b == 0x02) {
                containsIntegerTag = true;
                break;
            }
        }
        if (!containsIntegerTag) {
            throw new AssertionError("Encoding with zero CRL number does not contain DER INTEGER tag");
        }
    }
}
