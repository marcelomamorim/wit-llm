/* @test    @summary Test for PipedReader constructor with pipeSize parameter, including invalid input handling.    @run main PipedReaderWitupTest */

import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.IOException;

public class PipedReaderWitupTest {

    public static void main(String[] args) {
        testValidPipeSize();
        testInvalidPipeSize();
    }

    static void testValidPipeSize() {
        try {
            int validSize = 1024;
            PipedReader pr = new PipedReader(validSize);
            if (pr == null) {
                throw new AssertionError("PipedReader instance is null for valid size");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for valid pipeSize", e);
        }
    }

    static void testInvalidPipeSize() {
        int[] invalidSizes = {0, -1};
        for (int size : invalidSizes) {
            try {
                new PipedReader(size);
                throw new AssertionError("Expected IllegalArgumentException for pipeSize=" + size);
            } catch (IllegalArgumentException e) {
                // Expected exception, test passes for this case
            } catch (IOException e) {
                throw new AssertionError("Unexpected IOException for pipeSize=" + size, e);
            }
        }
    }
}
