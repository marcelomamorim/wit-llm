/* @test    @summary Test for CertificateSerialNumber constructor with DerValue input.    @run main CertificateSerialNumberWitupTest */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest {

    public static void main(String[] args) throws Exception {
        testValidDerValue();
        testIOExceptionOnInvalidTag();
        testIOExceptionOnZeroAvailableData();
    }

    private static void testValidDerValue() throws Exception {
        // Create a valid DerValue with a BigInteger
        BigInteger bigInt = BigInteger.valueOf(123456789);
        byte[] encoded = new byte[] {0x02, 0x04, 0x00, 0x07, 0x5B, (byte)0xCD, 0x15}; // DER encoding for integer 123456789
        DerInputStream dis = new DerInputStream(encoded);
        DerValue dv = dis.getDerValue();
        try {
            CertificateSerialNumber csn = new CertificateSerialNumber(dv);
            System.out.println("Valid DerValue test passed: " + csn.toString());
        } catch (IOException e) {
            System.err.println("Valid DerValue test failed: " + e);
        }
    }

    private static void testIOExceptionOnInvalidTag() {
        // Create a DerValue with an invalid tag (e.g., tag != 2)
        byte[] invalidTagBytes = new byte[] {0x01, 0x01, 0x00}; // Tag 1 instead of 2
        DerInputStream dis = new DerInputStream(invalidTagBytes);
        DerValue dv;
        try {
            dv = dis.getDerValue();
            new CertificateSerialNumber(dv);
            System.err.println("IOException on invalid tag test failed: exception was expected but not thrown.");
        } catch (IOException e) {
            System.out.println("IOException on invalid tag test passed: " + e.getMessage());
        }
    }

    private static void testIOExceptionOnZeroAvailableData() {
        // Create a DerValue with tag == 2 but no available data
        byte[] zeroDataBytes = new byte[] {0x02, 0x00}; // tag 2, length 0
        DerInputStream dis = new DerInputStream(zeroDataBytes);
        DerValue dv;
        try {
            dv = dis.getDerValue();
            new CertificateSerialNumber(dv);
            System.err.println("IOException on zero available data test failed: exception was expected but not thrown.");
        } catch (IOException e) {
            System.out.println("IOException on zero available data test passed: " + e.getMessage());
        }
    }
}
