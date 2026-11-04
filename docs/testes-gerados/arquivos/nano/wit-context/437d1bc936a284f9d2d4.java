/* @test    @summary Test DerValue.getBitString(boolean) for exception paths    @run main DerValueWitupTest */

import sun.security.util.DerValue;
import java.io.IOException;

public class DerValueWitupTest {

    public static void main(String[] args) throws Exception {
        testGetBitStringThrowsWhenTagNotBitString();
        testGetBitStringThrowsWhenTagIsBitStringAndBufferThrows();
        testGetBitStringReturnsBufferDataWhenTagIsBitStringAndNoException();
    }

    private static void testGetBitStringThrowsWhenTagNotBitString() {
        DerValue dv = new DerValue();
        dv.tag = 0x02; // Set to INTEGER, not BIT STRING
        dv.buffer = new DerInputBuffer(new byte[]{0x00});
        dv.data = new DerInputStream(dv.buffer);
        try {
            dv.getBitString(false);
            System.err.println("Expected IOException for non-bit string tag");
        } catch (IOException e) {
            // Expected exception
        }
    }

    private static void testGetBitStringThrowsWhenTagIsBitStringAndBufferThrows() {
        DerValue dv = new DerValue();
        dv.tag = DerValue.tag_BitString;
        dv.buffer = new DerInputBuffer(new byte[]{0x00});
        dv.data = new DerInputStream(dv.buffer);
        // Override buffer to throw IOException
        dv.buffer = new DerInputBuffer() {
            @Override
            public byte[] getBitString() throws IOException {
                throw new IOException("Buffer error");
            }
        };
        try {
            dv.getBitString(false);
            System.err.println("Expected IOException from buffer.getBitString()");
        } catch (IOException e) {
            // Expected exception
        }
    }

    private static void testGetBitStringReturnsBufferDataWhenNoException() {
        byte[] dataBytes = {0x01, 0x02, 0x03};
        DerInputBuffer buf = new DerInputBuffer(dataBytes);
        DerValue dv = new DerValue();
        dv.tag = DerValue.tag_BitString;
        dv.buffer = buf;
        dv.data = new DerInputStream(buf);
        try {
            byte[] result = dv.getBitString(false);
            if (result != dataBytes) {
                System.err.println("Unexpected result from getBitString");
            }
        } catch (IOException e) {
            System.err.println("Unexpected IOException: " + e);
        }
    }
}
