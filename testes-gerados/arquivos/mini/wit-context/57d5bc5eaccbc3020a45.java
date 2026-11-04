/*
 * @test
 * @summary Test sun.security.util.DerValue.getBitString() behavior
 * @run main DerValueWitupTest
 */

import sun.security.util.DerValue;
import sun.security.util.DerInputBuffer;
import java.io.IOException;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testGetBitStringWithValidTag();
        testGetBitStringWithInvalidTag();
    }

    private static void testGetBitStringWithValidTag() throws Exception {
        // Create a DerValue with tag_BitString (0x03) and some valid bit string data
        byte[] bitStringData = new byte[] { (byte)0x03, 0x03, 0x00, (byte)0xA0, 0x00 };
        // The first byte is the tag, second is length, then unused bits, then data
        DerValue dv = new DerValue(bitStringData);

        byte[] result = dv.getBitString();

        if (result == null || result.length == 0) {
            throw new RuntimeException("getBitString returned null or empty array for valid bit string");
        }

        // The returned bit string should match the data bytes after the unused bits byte
        // The buffer.getBitString() returns the bit string bytes excluding the unused bits byte
        // So expected is { (byte)0xA0, 0x00 }
        if (result.length != 2 || result[0] != (byte)0xA0 || result[1] != 0x00) {
            throw new RuntimeException("getBitString returned unexpected data");
        }
    }

    private static void testGetBitStringWithInvalidTag() {
        // Create a DerValue with tag_Integer (0x02) which is invalid for getBitString
        byte[] intData = new byte[] { (byte)0x02, 0x01, 0x01 };
        DerValue dv = new DerValue(intData);

        try {
            dv.getBitString();
            throw new RuntimeException("Expected IOException not thrown for invalid tag");
        } catch (IOException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not a bit string")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
