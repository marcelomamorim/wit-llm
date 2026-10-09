/* @test
   @summary Verifies the behavior of readShort() method in RandomAccessFile.
   @run main RandomAccessFileWitupTest_P31cc197bd3af
 */
import java.io.*;
public class RandomAccessFileWitupTest_P31cc197bd3af {
    public static void main(String[] args) throws Exception {
        File tempFile = File.createTempFile("testfile", ".bin");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            // Write known short value: 0x1234
            raf.writeByte(0x12);
            raf.writeByte(0x34);
            // Reset file pointer to beginning
            raf.seek(0);
            short result = raf.readShort();
            if (result != (short)0x1234) {
                throw new AssertionError("Expected 0x1234, but got: " + Integer.toHexString(result & 0xFFFF));
            }
        }
        // Test EOFException when reading beyond EOF
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "r")) {
            // Move to end of file
            raf.seek(raf.length());
            // Attempt to read short, should throw EOFException
            assertThrowsEOFException(() -> {
                raf.readShort();
            });
        }
    }
    private static void assertThrowsEOFException(Runnable r) {
        try {
            r.run();
            throw new AssertionError("Expected EOFException to be thrown");
        } catch (EOFException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Expected EOFException, but got: " + e);
        }
    }
}
