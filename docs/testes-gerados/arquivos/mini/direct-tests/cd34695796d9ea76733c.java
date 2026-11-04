/*
 * @test
 * @summary Test PipedReader(PipedWriter src, int pipeSize) constructor behavior
 * @run main PipedReaderWitupTest
 */

import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.IOException;

public class PipedReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testValidPipeSize();
        testZeroPipeSize();
        testNegativePipeSize();
    }

    static void testValidPipeSize() throws IOException {
        PipedWriter pw = new PipedWriter();
        // Use a valid pipe size
        int pipeSize = 128;
        PipedReader pr = new PipedReader(pw, pipeSize);
        if (pr == null) throw new RuntimeException("PipedReader constructor returned null");
    }

    static void testZeroPipeSize() {
        PipedWriter pw = new PipedWriter();
        try {
            new PipedReader(pw, 0);
            throw new RuntimeException("Expected IllegalArgumentException for pipeSize=0");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for pipeSize=0", e);
        }
    }

    static void testNegativePipeSize() {
        PipedWriter pw = new PipedWriter();
        try {
            new PipedReader(pw, -1);
            throw new RuntimeException("Expected IllegalArgumentException for pipeSize<0");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for pipeSize<0", e);
        }
    }
}
