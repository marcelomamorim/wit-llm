/* @test
   @summary Test readUnsignedByte method of RandomAccessFile.
   @run main RandomAccessFileWitupTest_Pbf80af80b9ce
 */
import java.io.*;
public class RandomAccessFileWitupTest_Pbf80af80b9ce {
    public static void main(String[] args) throws Exception {
        File tempFile = File.createTempFile("testfile", ".txt");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.write(new byte[] {10, 20, 30});
            raf.seek(0);
            int byte1 = raf.readUnsignedByte();
            int byte2 = raf.readUnsignedByte();
            int byte3 = raf.readUnsignedByte();
            if (byte1 != 10 || byte2 != 20 || byte3 != 30) {
                throw new AssertionError("Unexpected byte values: " + byte1 + ", " + byte2 + ", " + byte3);
            }
            raf.seek(100);
            try {
                raf.readUnsignedByte();
                throw new AssertionError("Expected EOFException not thrown");
            } catch (EOFException e) {
                // expected
            }
        }
    }
}
