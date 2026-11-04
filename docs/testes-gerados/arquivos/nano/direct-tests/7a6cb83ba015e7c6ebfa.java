/* @test    @summary Test readUnsignedShort method of RandomAccessFile    @run main RandomAccessFileWitupTest */

import java.io.File;
import java.io.RandomAccessFile;
import java.io.IOException;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws IOException {
        testReadUnsignedShort();
    }

    public static void testReadUnsignedShort() throws IOException {
        File tempFile = File.createTempFile("testfile", null);
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            // Write known unsigned short value: 0xABCD (43981 in decimal)
            raf.writeByte(0xAB);
            raf.writeByte(0xCD);
            // Reset file pointer to beginning
            raf.seek(0);
            int result = raf.readUnsignedShort();
            // 0xABCD = (0xAB << 8) + 0xCD = 43981
            if (result != 43981) {
                throw new AssertionError("Expected 43981, but got " + result);
            }
        }
    }
}
