/* @test
   @summary Verifies behavior of readInt method in RandomAccessFile.
   @run main RandomAccessFileWitupTest_Pef7b963b4d0b
 */
import java.io.*;
public class RandomAccessFileWitupTest_Pef7b963b4d0b {
    public static void main(String[] args) throws Exception {
        File tempFile = File.createTempFile("testfile", ".bin");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            // Write known integer value 0x01020304
            raf.writeByte(1);
            raf.writeByte(2);
            raf.writeByte(3);
            raf.writeByte(4);
            raf.seek(0);
            int result = raf.readInt();
            if (result != 0x01020304) {
                throw new AssertionError("Expected 0x01020304, but got: " + Integer.toHexString(result));
            }
        }
        // Test EOFException when file is too short
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "r")) {
            raf.setLength(2); // truncate file to 2 bytes
            raf.seek(0);
            try {
                raf.readInt();
                throw new AssertionError("Expected EOFException");
            } catch (EOFException e) {
                // expected
            }
        }
    }
}
