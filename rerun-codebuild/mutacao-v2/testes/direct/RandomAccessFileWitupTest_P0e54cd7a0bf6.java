/*
 * @test
 * @summary Test java.io.RandomAccessFile.seek(long) method behavior
 * @run main RandomAccessFileWitupTest_P0e54cd7a0bf6
 */

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest_P0e54cd7a0bf6 {
    public static void main(String[] args) throws Exception {
        testSeekNegativePositionThrows();
        testSeekValidPosition();
    }

    private static void testSeekNegativePositionThrows() {
        try {
            File tempFile = File.createTempFile("rafTest", ".tmp");
            tempFile.deleteOnExit();
            RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
            try {
                raf.seek(-1L);
                throw new RuntimeException("Expected IOException for negative seek offset");
            } catch (IOException e) {
                if (!"Negative seek offset".equals(e.getMessage())) {
                    throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
                }
            } finally {
                raf.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException during test setup", e);
        }
    }

    private static void testSeekValidPosition() throws IOException {
        File tempFile = File.createTempFile("rafTest", ".tmp");
        tempFile.deleteOnExit();
        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            raf.setLength(10L); // ensure file length
            raf.seek(5L);
            long pos = raf.getFilePointer();
            if (pos != 5L) {
                throw new RuntimeException("File pointer expected to be 5 but was " + pos);
            }
        } finally {
            raf.close();
        }
    }
}
