/* @test
   @summary Testa o método readByte() de RandomAccessFile para comportamento normal e exceções.
   @run main RandomAccessFileWitupTest_P9779da9cca54
 */
import java.io.RandomAccessFile;
import java.io.EOFException;
import java.io.IOException;
import java.io.File;

public class RandomAccessFileWitupTest_P9779da9cca54 {
    public static void main(String[] args) throws Exception {
        testReadByteNormal();
        testReadByteEOF();
    }

    private static void testReadByteNormal() throws Exception {
        File tempFile = File.createTempFile("test", ".bin");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.write(new byte[] {10, 20, 30});
            raf.seek(0);
            byte b1 = raf.readByte();
            byte b2 = raf.readByte();
            byte b3 = raf.readByte();
            if (b1 != 10 || b2 != 20 || b3 != 30) {
                throw new AssertionError("Unexpected byte values: " + b1 + ", " + b2 + ", " + b3);
            }
        }
    }

    private static void testReadByteEOF() throws Exception {
        File tempFile = File.createTempFile("testEOF", ".bin");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "r")) {
            raf.write(42); // write a single byte
            raf.seek(0);
            raf.readByte(); // should succeed
            raf.readByte(); // should throw EOFException
            throw new AssertionError("Expected EOFException was not thrown");
        } catch (EOFException e) {
            // expected
        }
    }
}
