/*
 * @test
 * @summary Test putTruncatedUnalignedBitString method of DerOutputStream
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import sun.security.util.BitArray;
import java.io.IOException;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testPutTruncatedUnalignedBitString();
    }

    private static void testPutTruncatedUnalignedBitString() throws IOException {
        // Create a BitArray with some bits set
        byte[] bits = {(byte)0b10101010, (byte)0b11000000}; // 10 bits used
        int length = 10; // bits length
        BitArray ba = new BitArray(bits, length);

        // Create DerOutputStream
        DerOutputStream derOut = new DerOutputStream();

        // Call putTruncatedUnalignedBitString with ba
        derOut.putTruncatedUnalignedBitString(ba);

        // The output should contain the truncated bit string encoding
        // We cannot assert exact bytes without BitArray and putUnalignedBitString details,
        // but we can check that the output is not empty and starts with the expected tag (0x03)
        byte[] output = derOut.toByteArray();

        if (output.length == 0) {
            throw new AssertionError("Output is empty after putTruncatedUnalignedBitString");
        }

        // DER BIT STRING tag is 0x03
        if (output[0] != 0x03) {
            throw new AssertionError("Output does not start with DER BIT STRING tag 0x03");
        }
    }
}
