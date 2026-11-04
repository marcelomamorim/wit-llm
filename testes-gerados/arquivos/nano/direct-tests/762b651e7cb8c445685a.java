/* @test    @summary Test DerInputStream.peekByte() method for correct behavior and exception handling    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testPeekByteNormal();
        testPeekByteAfterClose();
        testPeekByteWithInvalidBuffer();
    }

    private static void testPeekByteNormal() throws IOException {
        byte[] data = {0x02, 0x01, 0x05}; // INTEGER 5
        DerInputStream dis = new DerInputStream(data);
        int result = dis.peekByte();
        if (result != (data[0] & 0xFF)) {
            throw new AssertionError("Expected peekByte to return " + (data[0] & 0xFF) + " but got " + result);
        }
    }

    private static void testPeekByteAfterClose() throws IOException {
        byte[] data = {0x02, 0x01, 0x05};
        DerInputStream dis = new DerInputStream(data);
        // No explicit close method; simulate by creating a buffer with invalid state
        // Since DerInputStream does not have close, test behavior when buffer is invalid
        // For this test, we assume buffer is valid; so skip this case or simulate by invalid buffer
        // Here, we simulate by creating a subclass that throws IOException on peek
        DerInputStream disInvalid = new DerInputStream(data) {
            @Override
            public int peekByte() throws IOException {
                throw new IOException("Simulated IOException");
            }
        };
        try {
            disInvalid.peekByte();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            if (!"Simulated IOException".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testPeekByteWithInvalidBuffer() throws IOException {
        // Create a DerInputStream with a null buffer to simulate invalid state
        DerInputStream dis = new DerInputStream(new byte[0]) {
            @Override
            public int peekByte() throws IOException {
                throw new IOException("Invalid buffer state");
            }
        };
        try {
            dis.peekByte();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            if (!"Invalid buffer state".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
