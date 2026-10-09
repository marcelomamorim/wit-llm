/* @test    @summary Test for RandomAccessFile.seek() method handling of valid and invalid positions.    @run main RandomAccessFileWitupTest_Pbfcaff30e23e */

import java.io.RandomAccessFile;
import java.io.IOException;

public class RandomAccessFileWitupTest_Pbfcaff30e23e {

    public static void main(String[] args) {
        testSeekValidPosition();
        testSeekNegativePosition();
    }

    private static void testSeekValidPosition() {
        try {
            // Create a temporary file for testing
            String tempFileName = "temp_test_file.txt";
            RandomAccessFile raf = new RandomAccessFile(tempFileName, "rw");
            try {
                // Write some data
                raf.writeInt(12345);
                // Seek to position 0
                raf.seek(0);
                long position = raf.getFilePointer();
                // Seek to position 4 (after int)
                raf.seek(4);
                long newPosition = raf.getFilePointer();
                if (newPosition != 4) {
                    throw new AssertionError("seek did not set the correct position");
                }
            } finally {
                raf.close();
                new java.io.File(tempFileName).delete();
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException in testSeekValidPosition: " + e.getMessage(), e);
        }
    }

    private static void testSeekNegativePosition() {
        try {
            String tempFileName = "temp_test_file.txt";
            RandomAccessFile raf = new RandomAccessFile(tempFileName, "rw");
            try {
                boolean exceptionThrown = false;
                try {
                    raf.seek(-10);
                } catch (IOException e) {
                    exceptionThrown = true;
                    if (!e.getMessage().contains("Negative seek offset")) {
                        throw new AssertionError("Expected 'Negative seek offset' message");
                    }
                }
                if (!exceptionThrown) {
                    throw new AssertionError("Expected IOException for negative seek position");
                }
            } finally {
                raf.close();
                new java.io.File(tempFileName).delete();
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException in testSeekNegativePosition: " + e.getMessage(), e);
        }
    }
}
