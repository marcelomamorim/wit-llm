/*
 * @test
 * @summary Test PipedInputStream constructor with pipeSize parameter
 * @run main PipedInputStreamWitupTest
 */

import java.io.PipedInputStream;

public class PipedInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        // Test with a valid positive pipe size
        PipedInputStream pis = new PipedInputStream(128);
        if (pis == null) {
            throw new RuntimeException("PipedInputStream constructor returned null");
        }

        // Test with pipe size zero - expect IllegalArgumentException
        try {
            new PipedInputStream(0);
            throw new RuntimeException("Expected IllegalArgumentException for pipeSize=0");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test with negative pipe size - expect IllegalArgumentException
        try {
            new PipedInputStream(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative pipeSize");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
