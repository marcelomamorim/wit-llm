/*
 * @test
 * @summary Test constructor PipedInputStream(int pipeSize) for valid and invalid pipe sizes
 * @run main PipedInputStreamWitupTest
 */

import java.io.PipedInputStream;
import java.io.IOException;

public class PipedInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidPipeSize();
        testInvalidPipeSize();
    }

    private static void testValidPipeSize() throws IOException {
        // Test with a valid positive pipe size
        int validSize = 1024;
        PipedInputStream pis = new PipedInputStream(validSize);
        if (pis == null) {
            throw new AssertionError("PipedInputStream constructor returned null for valid pipe size");
        }
        // No exception expected, so just close the stream
        pis.close();
    }

    private static void testInvalidPipeSize() {
        // Test with zero pipe size, expect IllegalArgumentException
        try {
            new PipedInputStream(0);
            throw new AssertionError("Expected IllegalArgumentException for pipeSize=0");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for pipeSize=0", e);
        }

        // Test with negative pipe size, expect IllegalArgumentException
        try {
            new PipedInputStream(-1);
            throw new AssertionError("Expected IllegalArgumentException for pipeSize=-1");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for pipeSize=-1", e);
        }
    }
}
