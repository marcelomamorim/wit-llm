/* @test    @summary Test seek method for negative position to trigger IOException    @run main RandomAccessFileWitupTest_P7b574150c9cc */

import java.io.RandomAccessFile;
import java.io.IOException;

public class RandomAccessFileWitupTest_P7b574150c9cc {

    public static void main(String[] args) throws Exception {
        testSeekNegativeOffset();
    }

    private static void testSeekNegativeOffset() {
        try {
            // Instantiate with a temporary file path
            RandomAccessFile raf = new RandomAccessFile("tempfile.txt", "rw");
            try {
                // Call seek with a negative position to trigger IOException
                raf.seek(-1);
                throw new AssertionError("Expected IOException was not thrown for negative seek");
            } catch (IOException e) {
                // Verify that the exception message matches expected
                if (!"Negative seek offset".equals(e.getMessage())) {
                    throw new AssertionError("Unexpected exception message: " + e.getMessage());
                }
            } finally {
                raf.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create RandomAccessFile", e);
        }
    }
}
