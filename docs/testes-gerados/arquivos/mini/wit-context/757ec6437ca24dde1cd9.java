/*
 * @test
 * @summary Test constructor DerInputStream(DerInputBuffer buf) for basic behavior
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerInputBuffer;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        // Prepare a byte array with some DER data
        byte[] data = new byte[] {0x30, 0x03, 0x01, 0x01, 0xFF}; // valid DER sequence

        // Create DerInputBuffer from data
        DerInputBuffer dib = new DerInputBuffer(data);

        // Mark the buffer to allow reset
        dib.mark(Integer.MAX_VALUE);

        // Create DerInputStream using the constructor under test
        DerInputStream dis = new DerInputStream(dib);

        // Check that the buffer inside DerInputStream is the same as the one passed
        if (dis.buffer != dib) {
            throw new RuntimeException("DerInputStream buffer field does not match the input DerInputBuffer");
        }

        // Check that the buffer mark is still set (no exception means success)
        // No direct API to check mark state, so just call reset to verify mark was set
        dib.reset();

        // If reached here, test passed
        System.out.println("Test passed: DerInputStream(DerInputBuffer) constructor behaves as expected.");
    }
}
