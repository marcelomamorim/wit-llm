/*
 * @test
 * @summary Test sun.security.x509.SerialNumber constructor with DerValue input for expected IOException
 * @run main SerialNumberWitupTest
 */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.util.DerValue;
import sun.security.x509.SerialNumber;

public class SerialNumberWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerValue();
        testDerValueWithExcessData();
    }

    private static void testValidDerValue() throws Exception {
        // Create a DerValue with a valid INTEGER tag (tag == 2) and no excess data
        // Using a minimal valid encoding for INTEGER 1: 0x02 0x01 0x01
        byte[] validIntEncoding = new byte[] { 0x02, 0x01, 0x01 };
        DerValue val = new DerValue(validIntEncoding);
        SerialNumber sn = new SerialNumber(val);
        if (!sn.getNumber().equals(BigInteger.ONE)) {
            throw new RuntimeException("SerialNumber value mismatch for valid DerValue");
        }
    }

    private static void testDerValueWithExcessData() throws Exception {
        // Create a DerValue with valid INTEGER tag but with excess data to trigger IOException
        // Encoding: INTEGER 1 plus an extra byte 0x00 (excess data)
        byte[] invalidIntEncoding = new byte[] { 0x02, 0x02, 0x01, 0x00 };
        DerValue val = new DerValue(invalidIntEncoding);
        try {
            new SerialNumber(val);
            throw new RuntimeException("Expected IOException due to excess SerialNumber data not thrown");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
