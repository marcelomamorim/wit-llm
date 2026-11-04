/*
 * @test
 * @summary Witup-generated tests for PipedInputStream(int pipeSize) constructor
 * @run main PipedInputStreamWitupTest
 */

import java.io.PipedInputStream;

public class PipedInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testPositiveSizeCreatesEmptyStream();
        testNonPositiveSizeThrowsIllegalArgumentException();
    }

    static void testPositiveSizeCreatesEmptyStream() throws Exception {
        int[] sizes = {1, 16, 1024};
        for (int size : sizes) {
            PipedInputStream in = new PipedInputStream(size);
            try {
                if (in.available() != 0) {
                    throw new AssertionError("Expected no available bytes for size " + size);
                }
            } finally {
                in.close();
            }
        }
    }

    static void testNonPositiveSizeThrowsIllegalArgumentException() {
        int[] sizes = {0, -1, Integer.MIN_VALUE};
        for (int size : sizes) {
            boolean thrown = false;
            try {
                new PipedInputStream(size);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Expected IllegalArgumentException for size " + size);
            }
        }
    }
}
