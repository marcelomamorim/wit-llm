/*
 * @test
 * @summary Test sun.security.x509.SerialNumber constructor with InputStream for IOException paths
 * @run main SerialNumberWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.SerialNumber;

public class SerialNumberWitupTest {
    public static void main(String[] args) throws Exception {
        testValidSerialNumber();
        testInvalidTagThrowsIOException();
        testInsufficientDataThrowsIOException();
        testEmptyDataThrowsIOException();
    }

    private static void testValidSerialNumber() throws Exception {
        // DER encoding of INTEGER 1: 0x02 0x01 0x01
        byte[] validDer = new byte[] { 0x02, 0x01, 0x01 };
        ByteArrayInputStream bais = new ByteArrayInputStream(validDer);
        SerialNumber sn = new SerialNumber(bais);
        if (!sn.getNumber().equals(BigInteger.ONE)) {
            throw new RuntimeException("Valid serial number decoding failed");
        }
    }

    private static void testInvalidTagThrowsIOException() {
        // Invalid tag 0x03 (BIT STRING) instead of 0x02 (INTEGER)
        byte[] invalidTagDer = new byte[] { 0x03, 0x01, 0x01 };
        ByteArrayInputStream bais = new ByteArrayInputStream(invalidTagDer);
        try {
            new SerialNumber(bais);
            throw new RuntimeException("Expected IOException for invalid tag not thrown");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testInsufficientDataThrowsIOException() {
        // Tag 0x02 (INTEGER) but length byte too large (e.g. length 5 but only 3 bytes total)
        byte[] insufficientDataDer = new byte[] { 0x02, 0x05, 0x01, 0x02, 0x03 };
        ByteArrayInputStream bais = new ByteArrayInputStream(insufficientDataDer);
        try {
            new SerialNumber(bais);
            throw new RuntimeException("Expected IOException for insufficient data not thrown");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testEmptyDataThrowsIOException() {
        // Tag 0x02 (INTEGER) with zero length (0x00) - no content bytes
        byte[] emptyDataDer = new byte[] { 0x02, 0x00 };
        ByteArrayInputStream bais = new ByteArrayInputStream(emptyDataDer);
        try {
            new SerialNumber(bais);
            throw new RuntimeException("Expected IOException for empty data not thrown");
        } catch (IOException e) {
            // Expected
        }
    }
}
