/*
 * @test
 * @summary Test default constructor of PipedInputStream initializes buffer correctly
 * @run main PipedInputStreamWitupTest
 */

import java.io.PipedInputStream;

public class PipedInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        PipedInputStream pis = new PipedInputStream();
        // The default buffer size is 1024
        // We test that the buffer is allocated and in is -1 (empty buffer)
        if (pis.buffer == null) {
            throw new RuntimeException("buffer should not be null");
        }
        if (pis.buffer.length != 1024) {
            throw new RuntimeException("buffer length should be 1024 but was " + pis.buffer.length);
        }
        if (pis.in != -1) {
            throw new RuntimeException("in should be -1 after construction but was " + pis.in);
        }
        if (pis.out != 0) {
            throw new RuntimeException("out should be 0 after construction but was " + pis.out);
        }
    }
}
