/* @test
   @summary Verifies that readByte() throws EOFException at end of file.
   @run main RandomAccessFileWitupTest_P9779da9cca54
 */
import java.io.*;
public class RandomAccessFileWitupTest_P9779da9cca54 {
    public static void main(String[] args) throws Exception {
        File tempFile = File.createTempFile("testfile", ".tmp");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "r")) {
            byte b = raf.readByte();
            // Since file is empty, readByte() should throw EOFException
            throw new AssertionError("Expected EOFException not thrown");
        } catch (EOFException e) {
            // expected exception
        }
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.writeByte(42);
            raf.seek(0);
            byte b = raf.readByte();
            if (b != 42) {
                throw new AssertionError("Read byte does not match written byte");
            }
        }
    }
}
