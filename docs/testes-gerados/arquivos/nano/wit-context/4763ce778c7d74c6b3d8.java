/* @test    @summary Test for KeyUsageExtension constructors and encodeThis method    @run main KeyUsageExtensionWitupTest */

import sun.security.util.BitArray;
import sun.security.x509.KeyUsageExtension;
import java.io.IOException;

public class KeyUsageExtensionWitupTest {

    public static void main(String[] args) throws IOException {
        testConstructorWithBitArray();
        testEncodeThisWithValidBitArray();
        testEncodeThisWithEmptyBitArray();
    }

    private static void testConstructorWithBitArray() {
        try {
            boolean[] bits = {true, false, true};
            BitArray bitArray = new BitArray(bits);
            KeyUsageExtension ext = new KeyUsageExtension(bitArray);
            if (ext == null) {
                throw new AssertionError("Constructor returned null");
            }
        } catch (Exception e) {
            throw new AssertionError("Constructor with BitArray failed: " + e);
        }
    }

    private static void testEncodeThisWithValidBitArray() throws IOException {
        boolean[] bits = {true, false, true, false};
        BitArray bitArray = new BitArray(bits);
        KeyUsageExtension ext = new KeyUsageExtension(bitArray);
        try {
            ext.encodeThis();
        } catch (IOException e) {
            throw new AssertionError("encodeThis threw IOException: " + e);
        }
    }

    private static void testEncodeThisWithEmptyBitArray() throws IOException {
        boolean[] bits = {};
        BitArray bitArray = new BitArray(bits);
        KeyUsageExtension ext = new KeyUsageExtension(bitArray);
        try {
            ext.encodeThis();
        } catch (IOException e) {
            throw new AssertionError("encodeThis with empty array threw IOException: " + e);
        }
    }
}
