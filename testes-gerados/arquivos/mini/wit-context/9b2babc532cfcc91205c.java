/*
 * @test
 * @summary Test CertificateSerialNumber constructor with DerValue input for IOException paths
 * @run main CertificateSerialNumberWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateSerialNumber;
import sun.security.util.DerValue;

public class CertificateSerialNumberWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerValue();
        testInvalidTagDerValue();
        testInvalidLengthDerValue();
        testEmptyDataDerValue();
    }

    private static void testValidDerValue() throws Exception {
        // Create a DerValue with a valid INTEGER tag (0x02) and valid content
        // Using a minimal valid encoding for INTEGER 1: 0x02 0x01 0x01
        byte[] validInt = new byte[] { 0x02, 0x01, 0x01 };
        DerValue val = new DerValue(validInt);
        CertificateSerialNumber csn = new CertificateSerialNumber(val);
        String str = csn.toString();
        if (str == null || str.isEmpty()) {
            throw new RuntimeException("toString() returned null or empty for valid DerValue");
        }
    }

    private static void testInvalidTagDerValue() {
        // Create a DerValue with an invalid tag (not 0x02)
        // For example, tag 0x01 (BOOLEAN) with some content
        byte[] invalidTag = new byte[] { 0x01, 0x01, 0x00 };
        try {
            DerValue val = new DerValue(invalidTag);
            new CertificateSerialNumber(val);
            throw new RuntimeException("Expected IOException for invalid tag not thrown");
        } catch (IOException e) {
            // Expected exception
        }
    }

    private static void testInvalidLengthDerValue() {
        // Create a DerValue with tag 0x02 but length inconsistent with data
        // For example, length says 5 but only 2 bytes follow
        byte[] invalidLength = new byte[] { 0x02, 0x05, 0x01, 0x01 };
        try {
            DerValue val = new DerValue(invalidLength);
            new CertificateSerialNumber(val);
            throw new RuntimeException("Expected IOException for invalid length not thrown");
        } catch (IOException e) {
            // Expected exception
        }
    }

    private static void testEmptyDataDerValue() {
        // Create a DerValue with tag 0x02 but no data bytes
        byte[] emptyData = new byte[] { 0x02, 0x00 };
        try {
            DerValue val = new DerValue(emptyData);
            new CertificateSerialNumber(val);
            throw new RuntimeException("Expected IOException for empty data not thrown");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
