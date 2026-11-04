/*
 * @test
 * @summary Test sun.security.x509.CertificateSerialNumber.encode(OutputStream) for normal and edge cases
 * @run main CertificateSerialNumberWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithValidSerialNumber();
        testEncodeWithZeroSerialNumber();
        testEncodeWithNegativeSerialNumber();
    }

    private static void testEncodeWithValidSerialNumber() throws IOException {
        BigInteger bigInt = new BigInteger("123456789");
        CertificateSerialNumber csn = new CertificateSerialNumber(bigInt);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        csn.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty for valid serial number");
        }
    }

    private static void testEncodeWithZeroSerialNumber() throws IOException {
        CertificateSerialNumber csn = new CertificateSerialNumber(0);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        csn.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty for zero serial number");
        }
    }

    private static void testEncodeWithNegativeSerialNumber() throws IOException {
        BigInteger negative = new BigInteger("-1");
        CertificateSerialNumber csn = new CertificateSerialNumber(negative);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        csn.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty for negative serial number");
        }
    }
}
