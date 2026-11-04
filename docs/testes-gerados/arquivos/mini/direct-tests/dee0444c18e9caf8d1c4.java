/*
 * @test
 * @summary Test sun.security.util.DerValue.getUnalignedBitString method
 * @run main DerValueWitupTest
 */

import sun.security.util.DerValue;
import sun.security.util.BitArray;
import java.io.IOException;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testGetUnalignedBitStringWithValidBitStringTag();
        testGetUnalignedBitStringWithInvalidTag();
    }

    private static void testGetUnalignedBitStringWithValidBitStringTag() throws Exception {
        // Create a DerValue with tag_BitString and some valid bit string data
        // Use constructor DerValue(byte tag, byte[] data)
        byte tag = DerValue.tag_BitString;
        // Minimal valid bit string encoding: first byte is number of unused bits (0), followed by bits
        byte[] bitStringData = new byte[] {0x00, (byte)0xA0}; // 10100000 bits, 0 unused bits
        DerValue dv = new DerValue(tag, bitStringData);

        BitArray ba = dv.getUnalignedBitString();
        if (ba == null) {
            throw new RuntimeException("getUnalignedBitString returned null for valid bit string");
        }
        // Check that length matches expected bits (8 bits)
        if (ba.length() != 8) {
            throw new RuntimeException("BitArray length mismatch: expected 8, got " + ba.length());
        }
        // Check some bits
        if (!ba.get(0) || ba.get(1) || !ba.get(2)) {
            throw new RuntimeException("BitArray bits do not match expected pattern");
        }
    }

    private static void testGetUnalignedBitStringWithInvalidTag() {
        // Create a DerValue with a tag different from tag_BitString
        byte tag = DerValue.tag_Integer; // 0x02
        byte[] dummyData = new byte[] {0x01};
        DerValue dv = new DerValue(tag, dummyData);

        try {
            dv.getUnalignedBitString();
            throw new RuntimeException("Expected IOException not thrown for invalid tag");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not a bit string")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
