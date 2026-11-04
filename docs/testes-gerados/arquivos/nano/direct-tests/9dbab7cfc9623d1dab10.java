/* @test    @summary Test putTruncatedUnalignedBitString method for BitArray input    @run main DerOutputStreamWitupTest */

import sun.security.util.DerOutputStream;
import sun.security.util.BitArray;
import java.io.IOException;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testPutTruncatedUnalignedBitStringWithEmptyBitArray();
        testPutTruncatedUnalignedBitStringWithSingleBit();
        testPutTruncatedUnalignedBitStringWithMultipleBits();
    }

    static void testPutTruncatedUnalignedBitStringWithEmptyBitArray() throws IOException {
        BitArray emptyBitArray = new BitArray(0);
        DerOutputStream dos = new DerOutputStream();
        dos.putTruncatedUnalignedBitString(emptyBitArray);
        byte[] result = dos.toByteArray();
        // Expecting a single byte with the tag for BitString (0x03) and zero length
        assert result.length == 2 : "Expected length 2, got " + result.length;
        assert result[0] == 0x03 : "Expected tag 0x03, got " + Integer.toHexString(result[0] & 0xFF);
        assert result[1] == 0x00 : "Expected length 0, got " + (result[1] & 0xFF);
    }

    static void testPutTruncatedUnalignedBitStringWithSingleBit() throws IOException {
        BitArray bitArray = new BitArray(1);
        bitArray.setBit(0, true);
        DerOutputStream dos = new DerOutputStream();
        dos.putTruncatedUnalignedBitString(bitArray);
        byte[] result = dos.toByteArray();
        // Expecting tag, length, and bits
        assert result.length >= 3 : "Expected length >= 3, got " + result.length;
        assert result[0] == 0x03 : "Expected tag 0x03, got " + Integer.toHexString(result[0] & 0xFF);
        assert result[1] == 0x01 : "Expected length 1, got " + (result[1] & 0xFF);
        // The bits byte should have the first bit set
        assert (result[2] & 0x80) != 0 : "Expected first bit set in bits byte";
    }

    static void testPutTruncatedUnalignedBitStringWithMultipleBits() throws IOException {
        BitArray bitArray = new BitArray(5);
        bitArray.setBit(0, true);
        bitArray.setBit(2, true);
        bitArray.setBit(4, true);
        DerOutputStream dos = new DerOutputStream();
        dos.putTruncatedUnalignedBitString(bitArray);
        byte[] result = dos.toByteArray();
        // Expecting tag, length, and bits
        assert result.length >= 3 : "Expected length >= 3, got " + result.length;
        assert result[0] == 0x03 : "Expected tag 0x03, got " + Integer.toHexString(result[0] & 0xFF);
        assert result[1] == 1 : "Expected length 1, got " + (result[1] & 0xFF);
        // The bits byte should have bits 1, 3, 5 set (bits 0, 2, 4 in zero-based)
        int bitsByte = result[2] & 0xFF;
        assert (bitsByte & 0x80) != 0 : "First bit should be set";
        // Check that only the first 5 bits are used
        assert (bitsByte & 0x7F) == 0 : "Bits beyond 5 should be zero";
    }
}
