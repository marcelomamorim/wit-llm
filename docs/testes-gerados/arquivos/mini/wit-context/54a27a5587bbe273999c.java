/*
 * @test
 * @summary Test Base64.EncOutputStream.write(int) for correct behavior and exception handling
 * @run main Base64WitupTest
 */

import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.io.OutputStream;

public class Base64WitupTest {

    public static void main(String[] args) throws Exception {
        testWriteSingleByte();
        testWriteAfterCloseThrowsIOException();
        testWriteWithEmptyBufferThrowsArrayIndexOutOfBoundsException();
    }

    // Test normal write of a single byte through EncOutputStream
    private static void testWriteSingleByte() throws IOException {
        Encoder encoder = Base64.getEncoder();
        OutputStream os = encoder.wrap(new NullOutputStream());
        // Write a single byte and expect no exception
        os.write(0x41); // ASCII 'A'
        os.close();
    }

    // Test that writing after closing the stream throws IOException
    private static void testWriteAfterCloseThrowsIOException() throws IOException {
        Encoder encoder = Base64.getEncoder();
        OutputStream os = encoder.wrap(new NullOutputStream());
        os.close();
        boolean threw = false;
        try {
            os.write(0x42);
        } catch (IOException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IOException when writing after close");
        }
    }

    // Test that an internal ArrayIndexOutOfBoundsException can occur if buffer length < 1
    // This is a fragile internal state test; since we cannot create such state externally,
    // we document this test as a no-op to acknowledge the WIT expath but do not implement it.
    // This is because the buffer is created internally with length 1 in write(int).
    // So this test is omitted to avoid fragile reflection or internal state manipulation.

    // A dummy OutputStream that discards all bytes
    private static class NullOutputStream extends OutputStream {
        @Override
        public void write(int b) throws IOException {
            // discard
        }
    }
}
