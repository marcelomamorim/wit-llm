/* @test    @summary Test seek method of RandomAccessFile for valid and invalid positions    @run main RandomAccessFileWitupTest_P7b574150c9cc */

import java.io.File;
import java.io.RandomAccessFile;
import java.io.IOException;

public class RandomAccessFileWitupTest_P7b574150c9cc {

    public static void main(String[] args) {
        try {
            // Create a temporary file for testing
            File tempFile = File.createTempFile("testfile", ".tmp");
            tempFile.deleteOnExit();

            // Open RandomAccessFile in read-write mode
            try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
                // Write some data to the file
                raf.write(new byte[] {1, 2, 3, 4, 5});

                // Seek to a valid position (e.g., 2)
                raf.seek(2);
                long position = raf.getFilePointer();
                if (position != 2) {
                    throw new AssertionError("Expected file pointer at 2, but was at " + position);
                }

                // Seek to the beginning
                raf.seek(0);
                position = raf.getFilePointer();
                if (position != 0) {
                    throw new AssertionError("Expected file pointer at 0, but was at " + position);
                }

                // Seek to the end of the file
                long fileLength = raf.length();
                raf.seek(fileLength);
                position = raf.getFilePointer();
                if (position != fileLength) {
                    throw new AssertionError("Expected file pointer at " + fileLength + ", but was at " + position);
                }

                // Attempt to seek to a negative position, expect IOException
                try {
                    raf.seek(-1);
                    throw new AssertionError("Expected IOException for negative seek, but none was thrown");
                } catch (IOException e) {
                    // Expected exception
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
