/* @test    @summary Test readByte method of RandomAccessFile for normal and EOF conditions    @run main RandomAccessFileWitupTest */

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.File;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws IOException {
        testReadByteNormal();
        testReadByteEOF();
    }

    private static void testReadByteNormal() throws IOException {
        File tempFile = File.createTempFile("testReadByteNormal", null);
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.write(new byte[] {10, 20, 30});
            raf.seek(0);
            byte b1 = raf.readByte();
            byte b2 = raf.readByte();
            byte b3 = raf.readByte();
            if (b1 != 10 || b2 != 20 || b3 != 30) {
                throw new AssertionError("readByte returned incorrect values");
            }
        }
    }

    private static void testReadByteEOF() throws IOException {
        File tempFile = File.createTempFile("testReadByteEOF", null);
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.write(new byte[] {1, 2});
            raf.seek(2); // position at end
            try {
                raf.readByte();
                throw new AssertionError("Expected EOFException not thrown");
            } catch (EOFException e) {
                // expected
            }
        }
    }
}
