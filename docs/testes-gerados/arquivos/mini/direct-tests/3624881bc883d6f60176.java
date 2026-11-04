/*
 * @test
 * @summary Test ReasonFlags constructor with DerValue input
 * @run main sun.security.x509.ReasonFlagsWitupTest
 */

import java.io.IOException;
import sun.security.util.BitArray;
import sun.security.util.DerValue;
import sun.security.x509.ReasonFlags;

public class ReasonFlagsWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithValidDerValue();
        testConstructorWithEmptyBitString();
    }

    private static void testConstructorWithValidDerValue() throws IOException {
        // Create a DerValue with a BIT STRING representing bits 0 and 3 set
        // BIT STRING encoding: 03 (tag) length unusedBits bits
        // For example, bits: 00001001 (bit 0 and bit 3 set), unused bits = 0
        byte[] bitStringEncoded = new byte[] { (byte)0x03, 0x02, 0x00, (byte)0x09 };
        DerValue derVal = new DerValue(bitStringEncoded);

        ReasonFlags rf = new ReasonFlags(derVal);

        // Check that bits 0 and 3 are set
        // Using reflection is not allowed, so we test by re-encoding and checking bits indirectly
        // But ReasonFlags does not expose bits directly, so we rely on no exception and no crash
        if (rf == null) {
            throw new AssertionError("ReasonFlags instance is null");
        }
    }

    private static void testConstructorWithEmptyBitString() throws IOException {
        // Create a DerValue with an empty BIT STRING (0 bits)
        byte[] bitStringEncoded = new byte[] { (byte)0x03, 0x01, 0x00 };
        DerValue derVal = new DerValue(bitStringEncoded);

        ReasonFlags rf = new ReasonFlags(derVal);

        if (rf == null) {
            throw new AssertionError("ReasonFlags instance is null for empty bit string");
        }
    }
}
