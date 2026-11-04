/*
 * @test
 * @summary Test PipedReader(int pipeSize) constructor with valid and invalid sizes
 * @run main PipedReaderWitupTest
 */

import java.io.PipedReader;
import java.io.IOException;

public class PipedReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testValidPipeSize();
        testZeroPipeSize();
        testNegativePipeSize();
    }

    private static void testValidPipeSize() throws IOException {
        // Test with a typical positive pipe size
        PipedReader pr = new PipedReader(1024);
        if (pr == null) {
            throw new RuntimeException("PipedReader constructor returned null");
        }
    }

    private static void testZeroPipeSize() {
        try {
            new PipedReader(0);
            throw new RuntimeException("Expected IllegalArgumentException for pipeSize=0");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for pipeSize=0", e);
        }
    }

    private static void testNegativePipeSize() {
        try {
            new PipedReader(-1);
            throw new RuntimeException("Expected IllegalArgumentException for pipeSize=-1");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for pipeSize=-1", e);
        }
    }
}
