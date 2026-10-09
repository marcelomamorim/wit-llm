/* @test
   @summary Tests the readFully method of RandomAccessFile.
   @run main RandomAccessFileWitupTest_Pd05210c3a894
 */
import java.io.*;
public class RandomAccessFileWitupTest_Pd05210c3a894 {
    public static void main(String[] args) throws Exception {
        File tempFile = File.createTempFile("testfile", ".txt");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            byte[] data = {1, 2, 3, 4, 5};
            raf.write(data);
            raf.seek(0);
            byte[] buffer = new byte[5];
            // Test normal readFully
            raf.readFully(buffer, 0, buffer.length);
            for (int i = 0; i < buffer.length; i++) {
                if (buffer[i] != data[i]) {
                    throw new AssertionError("Data mismatch at index " + i);
                }
            }
            // Test readFully throws EOFException when reading beyond EOF
            byte[] smallBuffer = new byte[10];
            raf.seek(0);
            try {
                raf.readFully(smallBuffer, 0, smallBuffer.length);
                throw new AssertionError("Expected EOFException not thrown");
            } catch (EOFException e) {
                // expected
            }
        }
    }
}
