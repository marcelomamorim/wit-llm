/*
 * @test
 * @summary Test CertificateSerialNumber.encode(OutputStream) method
 * @run main CertificateSerialNumberWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest {
    public static void main(String[] args) throws IOException {
        testEncodeWithBigInteger();
        testEncodeWithInt();
    }

    private static void testEncodeWithBigInteger() throws IOException {
        BigInteger bigInt = new BigInteger("12345678901234567890");
        CertificateSerialNumber csn = new CertificateSerialNumber(bigInt);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        csn.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoding with BigInteger serial number produced empty output");
        }
    }

    private static void testEncodeWithInt() throws IOException {
        int num = 123456789;
        CertificateSerialNumber csn = new CertificateSerialNumber(num);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        csn.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoding with int serial number produced empty output");
        }
    }
}
