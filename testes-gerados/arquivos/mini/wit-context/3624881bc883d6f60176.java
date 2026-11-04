/*
 * @test
 * @summary Test sun.security.x509.ReasonFlags constructor with DerValue input
 * @run main ReasonFlagsWitupTest
 */

import sun.security.x509.ReasonFlags;
import sun.security.util.DerValue;
import java.io.IOException;

public class ReasonFlagsWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerValue();
        testInvalidDerValueThrows();
    }

    private static void testValidDerValue() throws IOException {
        // Create a DerValue with a valid BIT STRING tag (0x03) and some bits set
        // The byte array represents DER encoding of BIT STRING with 3 unused bits and bits 0 and 1 set
        // DER BIT STRING encoding: tag(0x03), length(0x02), unused bits(0x03), bits(0x60) -> bits 5 and 6 set
        byte[] derEncoded = new byte[] { 0x03, 0x02, 0x03, 0x60 };
        DerValue derVal = new DerValue(derEncoded);

        ReasonFlags rf = new ReasonFlags(derVal);

        // The constructor should not throw and bitString array should be non-null
        // We cannot access bitString directly, but no exception means success
        // No exception means test passed
    }

    private static void testInvalidDerValueThrows() {
        // Construct a DerValue with invalid tag (not 0x03) to trigger IOException or ArrayIndexOutOfBoundsException
        // According to source, DerValue.getUnalignedBitString(true) expects tag 0x03
        // We create a DerValue with tag 0x04 (OCTET STRING) to simulate invalid input
        byte[] invalidDerEncoded = new byte[] { 0x04, 0x01, 0x00 };
        try {
            DerValue derVal = new DerValue(invalidDerEncoded);
            // The following constructor call is expected to throw IOException or ArrayIndexOutOfBoundsException
            new ReasonFlags(derVal);
            throw new RuntimeException("Expected exception not thrown for invalid DerValue tag");
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            // Expected exception caught, test passed
        }
    }
}
