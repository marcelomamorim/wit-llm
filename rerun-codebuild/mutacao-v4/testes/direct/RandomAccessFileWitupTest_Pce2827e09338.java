/* @test
   @summary Tests the readBoolean() method of RandomAccessFile.
   @run main RandomAccessFileWitupTest_Pce2827e09338
 */
import java.io.*;
public class RandomAccessFileWitupTest_Pce2827e09338 {
    public static void main(String[] args) throws Exception {
        File tempFile = File.createTempFile("testfile", ".tmp");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            // Write a boolean value true (represented as 1)
            raf.write(1);
            // Reset file pointer to beginning
            raf.seek(0);
            // Read boolean, expect true
            boolean result = raf.readBoolean();
            if (result != true) {
                throw new AssertionError("Expected true, got " + result);
            }
            // Write a boolean value false (represented as 0)
            raf.seek(0);
            raf.write(0);
            // Reset file pointer
            raf.seek(0);
            // Read boolean, expect false
            boolean resultFalse = raf.readBoolean();
            if (resultFalse != false) {
                throw new AssertionError("Expected false, got " + resultFalse);
            }
            // Test EOFException when reading beyond file end
            raf.seek(0);
            raf.read(); // read one byte
            raf.seek(10); // seek beyond file size
            try {
                raf.readBoolean();
                throw new AssertionError("Expected EOFException");
            } catch (EOFException e) {
                // expected
            }
        }
    }
}
