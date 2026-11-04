/* @test    @summary Test PipedInputStream constructor with valid and invalid pipe sizes    @run main PipedInputStreamWitupTest */

import java.io.PipedInputStream;

public class PipedInputStreamWitupTest {

    public static void main(String[] args) {
        testValidPipeSize();
        testZeroPipeSize();
        testNegativePipeSize();
    }

    private static void testValidPipeSize() {
        try {
            int validSize = 1024;
            PipedInputStream pis = new PipedInputStream(validSize);
            assert pis != null : "PipedInputStream should be created with valid size";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid pipe size", e);
        }
    }

    private static void testZeroPipeSize() {
        try {
            new PipedInputStream(0);
            throw new AssertionError("Expected IllegalArgumentException for zero pipe size");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for zero pipe size", e);
        }
    }

    private static void testNegativePipeSize() {
        try {
            new PipedInputStream(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative pipe size");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for negative pipe size", e);
        }
    }
}
