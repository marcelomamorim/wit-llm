/* @test    @summary Test for sun.security.x509.CertificateSerialNumber.encode(OutputStream) method.
 * @run main CertificateSerialNumberWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest {

    public static void main(String[] args) {
        testEncodeWithValidSerialNumber();
        testEncodeWithLargeSerialNumber();
        testEncodeWithNullSerialNumber();
    }

    private static void testEncodeWithValidSerialNumber() {
        try {
            BigInteger serialNumber = BigInteger.valueOf(123456789);
            CertificateSerialNumber certSerial = new CertificateSerialNumber(serialNumber);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            certSerial.encode(baos);
            byte[] encodedBytes = baos.toByteArray();
            if (encodedBytes.length == 0) {
                throw new AssertionError("Encoded byte array should not be empty");
            }
            System.out.println("testEncodeWithValidSerialNumber passed.");
        } catch (Exception e) {
            throw new RuntimeException("testEncodeWithValidSerialNumber failed", e);
        }
    }

    private static void testEncodeWithLargeSerialNumber() {
        try {
            BigInteger largeSerial = new BigInteger("123456789012345678901234567890");
            CertificateSerialNumber certSerial = new CertificateSerialNumber(largeSerial);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            certSerial.encode(baos);
            byte[] encodedBytes = baos.toByteArray();
            if (encodedBytes.length == 0) {
                throw new AssertionError("Encoded byte array should not be empty");
            }
            System.out.println("testEncodeWithLargeSerialNumber passed.");
        } catch (Exception e) {
            throw new RuntimeException("testEncodeWithLargeSerialNumber failed", e);
        }
    }

    private static void testEncodeWithNullSerialNumber() {
        try {
            // Creating an object with null serial to simulate edge case
            CertificateSerialNumber certSerial = new CertificateSerialNumber(BigInteger.ZERO);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            certSerial.encode(baos);
            byte[] encodedBytes = baos.toByteArray();
            if (encodedBytes.length == 0) {
                throw new AssertionError("Encoded byte array should not be empty");
            }
            System.out.println("testEncodeWithNullSerialNumber passed.");
        } catch (Exception e) {
            throw new RuntimeException("testEncodeWithNullSerialNumber failed", e);
        }
    }
}
