/* @test    @summary Test for RandomAccessFile.readFully method handling EOFException    @run main RandomAccessFileWitupTest_Paf73db1d8419 */

import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.EOFException;

public class RandomAccessFileWitupTest_Paf73db1d8419 {

    public static void main(String[] args) throws Exception {
        testReadFullyThrowsEOFExceptionOnEarlyEOF();
    }

    public static void testReadFullyThrowsEOFExceptionOnEarlyEOF() throws IOException {
        byte[] buffer = new byte[10];
        // Create a temporary file with less than 10 bytes
        java.io.File tempFile = java.io.File.createTempFile("testfile", null);
        try {
            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(tempFile)) {
                fos.write(new byte[]{1, 2, 3, 4, 5}); // 5 bytes
            }
            try (RandomAccessFile raf = new RandomAccessFile(tempFile, "r")) {
                // Attempt to read 10 bytes, should throw EOFException
                try {
                    raf.readFully(buffer, 0, 10);
                    // If no exception thrown, test fails
                    System.err.println("FAIL: Expected EOFException was not thrown.");
                    System.exit(1);
                } catch (EOFException e) {
                    // Expected exception caught
                    System.out.println("PASS: EOFException thrown as expected.");
                }
            }
        } finally {
            tempFile.delete();
        }
    }
}
