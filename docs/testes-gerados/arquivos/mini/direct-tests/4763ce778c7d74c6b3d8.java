/*
 * @test
 * @summary Test KeyUsageExtension(BitArray) constructor behavior
 * @run main KeyUsageExtensionWitupTest
 */

import sun.security.x509.KeyUsageExtension;
import sun.security.util.BitArray;
import java.io.IOException;

public class KeyUsageExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithEmptyBitArray();
        testConstructorWithSingleBitSet();
        testConstructorWithMultipleBitsSet();
    }

    private static void testConstructorWithEmptyBitArray() throws IOException {
        boolean[] bits = new boolean[0];
        BitArray ba = new BitArray(bits);
        KeyUsageExtension ext = new KeyUsageExtension(ba);
        // The extensionValue should not be null or empty
        if (ext.extensionValue == null || ext.extensionValue.length == 0) {
            throw new AssertionError("extensionValue is null or empty for empty bit array");
        }
    }

    private static void testConstructorWithSingleBitSet() throws IOException {
        boolean[] bits = new boolean[1];
        bits[0] = true;
        BitArray ba = new BitArray(bits);
        KeyUsageExtension ext = new KeyUsageExtension(ba);
        if (ext.extensionValue == null || ext.extensionValue.length == 0) {
            throw new AssertionError("extensionValue is null or empty for single bit set");
        }
    }

    private static void testConstructorWithMultipleBitsSet() throws IOException {
        boolean[] bits = new boolean[9];
        bits[0] = true;  // digital_signature
        bits[3] = true;  // data_encipherment
        bits[8] = true;  // decipher_only
        BitArray ba = new BitArray(bits);
        KeyUsageExtension ext = new KeyUsageExtension(ba);
        if (ext.extensionValue == null || ext.extensionValue.length == 0) {
            throw new AssertionError("extensionValue is null or empty for multiple bits set");
        }
    }
}
