/*
 * @test
 * @summary Test sun.security.util.DerOutputStream.putTruncatedUnalignedBitString(BitArray) for basic behavior
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import sun.security.util.BitArray;
import java.io.IOException;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        DerOutputStream dos = new DerOutputStream();

        // Create a BitArray with some bits set
        byte[] bits = {(byte)0b10101010, (byte)0b11000000};
        int length = 10; // 10 bits used
        BitArray ba = new BitArray(bits, length);

        // Call putTruncatedUnalignedBitString with a BitArray that truncates to itself
        // The truncate method returns a BitArray, so we test that the method completes without exception
        dos.putTruncatedUnalignedBitString(ba);

        // The output buffer should contain the encoded bit string
        byte[] output = dos.toByteArray();

        // Basic checks: output length > 0 and first byte is the tag for BIT STRING (0x03)
        if (output.length == 0) {
            throw new AssertionError("Output is empty");
        }
        if (output[0] != 0x03) {
            throw new AssertionError("Output does not start with BIT STRING tag (0x03), got: " + output[0]);
        }

        // Additional check: length byte matches output length - 2 (tag + length byte)
        int lengthByte = output[1] & 0xFF;
        if (lengthByte != output.length - 2) {
            throw new AssertionError("Length byte does not match actual length: " + lengthByte + " vs " + (output.length - 2));
        }
    }
}
