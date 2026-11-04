/* @test    @summary Test encoding of CertificateSerialNumber.    @run main CertificateSerialNumberWitupTest */

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
        CertificateSerialNumber serialNumber = new CertificateSerialNumber(bigInt);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        serialNumber.encode(baos);
        byte[] encodedBytes = baos.toByteArray();
        if (encodedBytes.length == 0) {
            throw new AssertionError("Encoding with BigInteger resulted in empty byte array");
        }
        System.out.println("testEncodeWithBigInteger passed.");
    }

    private static void testEncodeWithInt() throws IOException {
        int num = 987654321;
        CertificateSerialNumber serialNumber = new CertificateSerialNumber(num);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        serialNumber.encode(baos);
        byte[] encodedBytes = baos.toByteArray();
        if (encodedBytes.length == 0) {
            throw new AssertionError("Encoding with int resulted in empty byte array");
        }
        System.out.println("testEncodeWithInt passed.");
    }
}
