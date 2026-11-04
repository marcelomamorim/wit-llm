/*
 * @test
 * @summary Test sun.security.util.DerValue.getBitString(boolean) method
 * @run main DerValueWitupTest
 */

import sun.security.util.DerValue;
import sun.security.util.DerInputBuffer;
import java.io.IOException;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testGetBitStringWithCorrectTag();
        testGetBitStringWithIncorrectTag();
    }

    private static void testGetBitStringWithCorrectTag() throws Exception {
        // Create a DerValue with tag_BitString and a buffer containing a known bit string
        byte[] bitStringData = new byte[] { (byte)0x03, 0x02, 0x05, (byte)0xA0 };
        // 0x03 = tag_BitString, 0x02 = length, 0x05 = unused bits, 0xA0 = bits
        DerValue dv = new DerValue(bitStringData);

        // getBitString with tagImplicit = false should succeed and return the bit string bytes
        byte[] result = dv.getBitString(false);

        // The returned bit string should be the bytes after the unused bits byte
        // i.e. { (byte)0xA0 }
        if (result == null || result.length != 1 || result[0] != (byte)0xA0) {
            throw new RuntimeException("testGetBitStringWithCorrectTag failed: unexpected bit string result");
        }

        // getBitString with tagImplicit = true should also succeed and return the same
        byte[] resultImplicit = dv.getBitString(true);
        if (resultImplicit == null || resultImplicit.length != 1 || resultImplicit[0] != (byte)0xA0) {
            throw new RuntimeException("testGetBitStringWithCorrectTag failed: unexpected bit string result with tagImplicit true");
        }
    }

    private static void testGetBitStringWithIncorrectTag() throws Exception {
        // Create a DerValue with tag_Integer (0x02) but data that looks like a bit string
        byte[] intData = new byte[] { (byte)0x02, 0x01, 0x01 };
        DerValue dv = new DerValue(intData);

        // getBitString with tagImplicit = false should throw IOException because tag != tag_BitString
        boolean threw = false;
        try {
            dv.getBitString(false);
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not a bit string")) {
                throw new RuntimeException("testGetBitStringWithIncorrectTag failed: wrong exception message");
            }
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("testGetBitStringWithIncorrectTag failed: IOException not thrown");
        }

        // getBitString with tagImplicit = true should not throw, returns buffer.getBitString()
        // but since tag is not checked, it should succeed
        byte[] result = dv.getBitString(true);
        if (result == null || result.length != 1 || result[0] != 0x01) {
            throw new RuntimeException("testGetBitStringWithIncorrectTag failed: unexpected bit string result with tagImplicit true");
        }
    }
}