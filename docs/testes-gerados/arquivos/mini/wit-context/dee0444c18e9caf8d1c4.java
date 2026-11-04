/*
 * @test
 * @summary Test sun.security.util.DerValue.getUnalignedBitString() behavior
 * @run main DerValueWitupTest
 */

import sun.security.util.DerValue;
import sun.security.util.BitArray;
import java.io.IOException;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testGetUnalignedBitStringWithValidTag();
        testGetUnalignedBitStringWithInvalidTag();
    }

    private static void testGetUnalignedBitStringWithValidTag() throws Exception {
        // Create a DerValue with tag_BitString (0x03) and some dummy data
        byte[] data = new byte[] { (byte)0x03, 0x01, (byte)0x80 }; // tag=3, length=1, data=0x80
        DerValue dv = new DerValue(data);

        // The tag field should be tag_BitString
        if (dv.tag != DerValue.tag_BitString) {
            throw new RuntimeException("Setup failed: DerValue tag is not tag_BitString");
        }

        BitArray ba = dv.getUnalignedBitString();
        if (ba == null) {
            throw new RuntimeException("getUnalignedBitString returned null");
        }

        // Check that the BitArray length is 8 (1 byte * 8 bits)
        if (ba.length() != 8) {
            throw new RuntimeException("BitArray length expected 8 but was " + ba.length());
        }

        // Check that the first bit is set (0x80 = 10000000)
        if (!ba.get(0)) {
            throw new RuntimeException("First bit expected to be true");
        }

        // Check that the second bit is false
        if (ba.get(1)) {
            throw new RuntimeException("Second bit expected to be false");
        }
    }

    private static void testGetUnalignedBitStringWithInvalidTag() {
        // Create a DerValue with tag != tag_BitString (e.g., tag_Integer = 0x02)
        byte[] data = new byte[] { (byte)0x02, 0x01, 0x01 }; // tag=2, length=1, data=0x01
        DerValue dv = new DerValue(data);

        try {
            dv.getUnalignedBitString();
            throw new RuntimeException("Expected IOException not thrown for invalid tag");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not a bit string")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
