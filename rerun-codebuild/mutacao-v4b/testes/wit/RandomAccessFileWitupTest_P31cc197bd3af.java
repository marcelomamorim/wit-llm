/* @test
   @summary Tests readShort() method of RandomAccessFile for normal and EOF conditions.
   @run main RandomAccessFileWitupTest_P31cc197bd3af
 */
import java.io.*;
public class RandomAccessFileWitupTest_P31cc197bd3af {
    public static void main(String[] args) throws Exception {
        testReadShortNormal();
        testReadShortEOF();
    }

    private static void testReadShortNormal() throws Exception {
        File tempFile = File.createTempFile("test", ".bin");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.writeShort(0x1234);
            raf.seek(0);
            short result = raf.readShort();
            if (result != (short)0x1234) {
                throw new AssertionError("Expected 0x1234, got: " + Integer.toHexString(result & 0xFFFF));
            }
        }
    }

    private static void testReadShortEOF() throws Exception {
        File tempFile = File.createTempFile("testEOF", ".bin");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.writeByte(0x12);
            raf.seek(0);
            raf.readByte(); // read one byte
            try {
                raf.readShort();
                throw new AssertionError("Expected EOFException not thrown");
            } catch (EOFException e) {
                // expected
            }
        }
    }
}
