/*
 * @test
 * @summary Test PipedReader(int pipeSize) constructor for valid and invalid pipe sizes
 * @run main PipedReaderWitupTest
 */

import java.io.PipedReader;
import java.io.IOException;

public class PipedReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testValidPipeSize();
        testInvalidPipeSize();
    }

    private static void testValidPipeSize() throws IOException {
        // Test with a valid positive pipe size
        int validSize = 10;
        PipedReader pr = new PipedReader(validSize);
        if (pr == null) {
            throw new AssertionError("PipedReader constructor returned null for valid pipe size");
        }
    }

    private static void testInvalidPipeSize() {
        // Test with zero pipe size, expect IllegalArgumentException
        try {
            new PipedReader(0);
            throw new AssertionError("Expected IllegalArgumentException for pipeSize=0");
        } catch (IllegalArgumentException expected) {
        }

        // Test with negative pipe size, expect IllegalArgumentException
        try {
            new PipedReader(-1);
            throw new AssertionError("Expected IllegalArgumentException for pipeSize=-1");
        } catch (IllegalArgumentException expected) {
        }
    }
}
