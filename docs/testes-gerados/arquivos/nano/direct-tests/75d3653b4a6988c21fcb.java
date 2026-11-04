/* @test    @summary Test seek method of RandomAccessFile for valid and invalid positions    @run main RandomAccessFileWitupTest */

import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.File;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws IOException {
        testSeekValidPosition();
        testSeekNegativePosition();
    }

    private static void testSeekValidPosition() throws IOException {
        File tempFile = File.createTempFile("testfile", ".tmp");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            long position = 10L;
            raf.seek(position);
            long filePointer = raf.getFilePointer();
            if (filePointer != position) {
                throw new AssertionError("Expected file pointer to be at " + position + " but was " + filePointer);
            }
        }
    }

    private static void testSeekNegativePosition() {
        File tempFile;
        try {
            tempFile = File.createTempFile("testfile", ".tmp");
            tempFile.deleteOnExit();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create temp file", e);
        }
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            long negativePosition = -1L;
            try {
                raf.seek(negativePosition);
                throw new AssertionError("Expected IOException for negative seek position");
            } catch (IOException e) {
                if (!e.getMessage().contains("Negative seek offset")) {
                    throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to open RandomAccessFile", e);
        }
    }
}
