/* @test    @summary Test KeyUsageExtension constructor with BitArray input    @run main KeyUsageExtensionWitupTest */

import sun.security.x509.KeyUsageExtension;
import sun.security.util.BitArray;
import java.io.IOException;

public class KeyUsageExtensionWitupTest {

    public static void main(String[] args) throws IOException {
        testConstructorWithBitArrayAllFalse();
        testConstructorWithBitArrayAllTrue();
        testConstructorWithBitArrayMixed();
    }

    static void testConstructorWithBitArrayAllFalse() throws IOException {
        BitArray bits = new BitArray(9); // all false by default
        KeyUsageExtension ext = new KeyUsageExtension(bits);
        assert ext != null : "Extension should not be null";
    }

    static void testConstructorWithBitArrayAllTrue() throws IOException {
        BitArray bits = new BitArray(9);
        for (int i = 0; i < 9; i++) {
            bits.set(i, true);
        }
        KeyUsageExtension ext = new KeyUsageExtension(bits);
        assert ext != null : "Extension should not be null";
    }

    static void testConstructorWithBitArrayMixed() throws IOException {
        BitArray bits = new BitArray(9);
        bits.set(0, true);
        bits.set(4, true);
        bits.set(8, true);
        KeyUsageExtension ext = new KeyUsageExtension(bits);
        assert ext != null : "Extension should not be null";
    }
}
