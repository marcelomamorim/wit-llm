/* @test    @summary Test DerInputStream.getDerValue() method for correct behavior and exception handling.    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testValidDerValue();
        testEmptyBufferThrows();
        testInvalidBufferThrows();
    }

    static void testValidDerValue() throws IOException {
        byte[] data = {0x30, 0x03, 0x02, 0x01, 0x05}; // DER encoding for sequence with integer 5
        DerInputStream dis = new DerInputStream(data);
        DerValue dv = dis.getDerValue();
        assert dv != null : "DerValue should not be null";
        assert dv.tag == 0x30 : "Expected tag 0x30 for sequence";
    }

    static void testEmptyBufferThrows() {
        byte[] emptyData = {};
        try {
            DerInputStream dis = new DerInputStream(emptyData);
            dis.getDerValue();
            assert false : "Expected IOException for empty buffer";
        } catch (IOException e) {
            assert e.getMessage().contains("EOF") : "Expected EOFException message";
        }
    }

    static void testInvalidBufferThrows() {
        byte[] invalidData = {(byte)0xFF, (byte)0xFF}; // invalid DER data
        try {
            DerInputStream dis = new DerInputStream(invalidData);
            dis.getDerValue();
            // Depending on implementation, may throw IOException or DerValue with invalid tag
            // We check for IOException here
            assert false : "Expected IOException for invalid DER data";
        } catch (IOException e) {
            // Expected exception
        }
    }
}
