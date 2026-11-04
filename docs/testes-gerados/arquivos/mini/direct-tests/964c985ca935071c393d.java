/*
 * @test
 * @summary Test sun.security.x509.CRLNumberExtension.encode(OutputStream) method
 * @run main CRLNumberExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;
import sun.security.x509.PKIXExtensions;

public class CRLNumberExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithIntConstructor();
        testEncodeWithBigIntegerConstructor();
    }

    private static void testEncodeWithIntConstructor() throws IOException {
        CRLNumberExtension ext = new CRLNumberExtension(12345);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty for int constructor");
        }
    }

    private static void testEncodeWithBigIntegerConstructor() throws IOException {
        CRLNumberExtension ext = new CRLNumberExtension(BigInteger.valueOf(67890L));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty for BigInteger constructor");
        }
    }
}
