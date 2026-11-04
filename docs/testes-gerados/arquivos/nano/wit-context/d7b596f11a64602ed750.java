/* @test    @summary Test readInt() method for EOFException behavior    @run main RandomAccessFileWitupTest */

import java.io.EOFException;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws Exception {
        testReadIntEOFException();
    }

    private static void testReadIntEOFException() {
        File tempFile = null;
        RandomAccessFile raf = null;
        try {
            // Create a temporary file with no data
            tempFile = File.createTempFile("testfile", null);
            tempFile.deleteOnExit();
            // Open in read mode
            raf = new RandomAccessFile(tempFile, "r");
            // Move to end of file
            raf.seek(0);
            // Attempt to read int, expecting EOFException
            boolean exceptionThrown = false;
            try {
                raf.readInt();
            } catch (EOFException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected EOFException was not thrown");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                }
            }
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
