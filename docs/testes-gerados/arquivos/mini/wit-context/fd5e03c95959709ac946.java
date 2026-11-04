/*
 * @test
 * @summary Test basic construction and buffer initialization of PipedInputStream()
 * @run main PipedInputStreamWitupTest
 */

import java.io.PipedInputStream;

public class PipedInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        // Test default constructor initializes buffer with default size
        PipedInputStream pis = new PipedInputStream();
        // The default pipe size is 1024, so buffer length should be at least 1024
        if (pis.buffer == null) {
            throw new AssertionError("buffer should not be null");
        }
        if (pis.buffer.length < 1024) {
            throw new AssertionError("buffer length " + pis.buffer.length + " is less than default pipe size 1024");
        }
        // The 'in' field should be -1 indicating empty buffer
        if (pis.in != -1) {
            throw new AssertionError("in field expected to be -1 but was " + pis.in);
        }
        // The 'out' field should be 0
        if (pis.out != 0) {
            throw new AssertionError("out field expected to be 0 but was " + pis.out);
        }
    }
}
