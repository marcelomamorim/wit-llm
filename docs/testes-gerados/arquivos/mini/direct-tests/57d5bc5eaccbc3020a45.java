/*
 * @test
 * @summary Test sun.security.util.DerValue.getBitString() method behavior
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
        // Create a DerValue with tag_BitString and a buffer that returns a known bit string
        final byte[] expectedBits = new byte[] { (byte)0xAA, (byte)0x55 };

        DerValue dv = new DerValue((byte)DerValue.tag_BitString, new byte[] { 0x03, 0x02, (byte)0xAA, 0x55 });

        // The constructor above sets tag and buffer internally
        // We rely on getBitString() returning the buffer's getBitString()
        byte[] actualBits = dv.getBitString();

        if (actualBits == null) {
            throw new RuntimeException("getBitString() returned null");
        }

        if (actualBits.length != expectedBits.length) {
            throw new RuntimeException("getBitString() returned wrong length: expected " + expectedBits.length + " but got " + actualBits.length);
        }

        for (int i = 0; i < expectedBits.length; i++) {
            if (actualBits[i] != expectedBits[i]) {
                throw new RuntimeException("getBitString() returned wrong data at index " + i + ": expected " + expectedBits[i] + " but got " + actualBits[i]);
            }
        }
    }

    private static void testGetBitStringWithInvalidTag() {
        // Create a DerValue with a tag different from tag_BitString
        DerValue dv = new DerValue((byte)DerValue.tag_Integer, new byte[] { 0x02, 0x01, 0x01 });

        try {
            dv.getBitString();
            throw new RuntimeException("Expected IOException not thrown for invalid tag");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not a bit string")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
