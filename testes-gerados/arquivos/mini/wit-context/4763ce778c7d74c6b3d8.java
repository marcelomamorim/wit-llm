/*
 * @test
 * @summary Test KeyUsageExtension(BitArray) constructor for correct encoding and behavior
 * @run main KeyUsageExtensionWitupTest
 */

import sun.security.x509.KeyUsageExtension;
import sun.security.util.BitArray;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class KeyUsageExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testValidBitArray();
        testEmptyBitArray();
    }

    private static void testValidBitArray() throws IOException {
        // Create a BitArray with some bits set
        boolean[] bits = new boolean[9];
        bits[0] = true;  // digital_signature
        bits[3] = true;  // data_encipherment
        BitArray bitArray = new BitArray(bits);

        KeyUsageExtension ext = new KeyUsageExtension(bitArray);

        // The extensionValue should not be null or empty
        byte[] encoded = ext.getExtensionValue();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded extensionValue is null or empty");
        }

        // Decode the extensionValue to verify it contains the expected bits
        DerInputStream derIn = new DerInputStream(encoded);
        DerValue derVal = derIn.getDerValue();
        BitArray decodedBits = derVal.getUnalignedBitString(true);

        // Check bits set match original
        if (decodedBits.length() < bits.length) {
            throw new RuntimeException("Decoded BitArray length less than original");
        }
        for (int i = 0; i < bits.length; i++) {
            if (decodedBits.getBit(i) != bits[i]) {
                throw new RuntimeException("Bit mismatch at position " + i);
            }
        }
    }

    private static void testEmptyBitArray() throws IOException {
        // Test with empty BitArray (length 0)
        BitArray emptyBitArray = new BitArray(new boolean[0]);
        KeyUsageExtension ext = new KeyUsageExtension(emptyBitArray);

        byte[] encoded = ext.getExtensionValue();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded extensionValue is null or empty for empty BitArray");
        }

        // Decode and verify length is zero
        DerInputStream derIn = new DerInputStream(encoded);
        DerValue derVal = derIn.getDerValue();
        BitArray decodedBits = derVal.getUnalignedBitString(true);

        if (decodedBits.length() != 0) {
            throw new RuntimeException("Decoded BitArray length expected 0 but was " + decodedBits.length());
        }
    }
}
