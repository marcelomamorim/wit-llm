/* @test    @summary Test for RandomAccessFile.readByte() method to verify behavior at EOF and normal operation    @run main RandomAccessFileWitupTest_P7e5eb9c6ac49 */

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest_P7e5eb9c6ac49 {

    public static void main(String[] args) throws IOException {
        testReadByteNormal();
        testReadByteEOF();
    }

    private static void testReadByteNormal() throws IOException {
        byte[] data = {10, 20, 30};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        // Using a temporary file with RandomAccessFile
        java.io.File tempFile = java.io.File.createTempFile("test", null);
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.write(data);
            raf.seek(0);
            byte b1 = raf.readByte();
            byte b2 = raf.readByte();
            byte b3 = raf.readByte();
            if (b1 != 10 || b2 != 20 || b3 != 30) {
                throw new AssertionError("Normal readByte() failed: values mismatch");
            }
        }
    }

    private static void testReadByteEOF() throws IOException {
        byte[] data = {1};
        java.io.File tempFile = java.io.File.createTempFile("testEOF", null);
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.write(data);
            raf.seek(0);
            raf.readByte(); // should succeed
            try {
                raf.readByte(); // should throw EOFException
                throw new AssertionError("Expected EOFException not thrown");
            } catch (EOFException e) {
                // expected
            }
        }
    }
}
