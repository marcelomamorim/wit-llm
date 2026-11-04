/*
 * @test
 * @summary Test PipedReader(PipedWriter src, int pipeSize) constructor for valid and invalid pipeSize
 * @run main PipedReaderWitupTest
 */

import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.IOException;

public class PipedReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testValidPipeSize();
        testInvalidPipeSize();
    }

    private static void testValidPipeSize() throws IOException {
        PipedWriter writer = new PipedWriter();
        // Use a valid pipe size
        int pipeSize = 1024;
        PipedReader reader = new PipedReader(writer, pipeSize);
        if (reader == null) {
            throw new AssertionError("PipedReader constructor returned null for valid pipeSize");
        }
    }

    private static void testInvalidPipeSize() {
        PipedWriter writer = new PipedWriter();
        int[] invalidSizes = {0, -1, -100};
        for (int size : invalidSizes) {
            try {
                new PipedReader(writer, size);
                throw new AssertionError("Expected IllegalArgumentException for pipeSize=" + size);
            } catch (IllegalArgumentException e) {
                // Expected exception
            } catch (IOException e) {
                throw new AssertionError("Unexpected IOException for pipeSize=" + size + ": " + e);
            }
        }
    }
}
