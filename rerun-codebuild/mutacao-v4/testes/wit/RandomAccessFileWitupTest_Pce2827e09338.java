/* @test
   @summary Testa o método readBoolean() de RandomAccessFile para comportamento normal e exceções.
   @run main RandomAccessFileWitupTest_Pce2827e09338
 */
import java.io.RandomAccessFile;
import java.io.EOFException;
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest_Pce2827e09338 {
    public static void main(String[] args) throws Exception {
        testReadBooleanNormal();
        testReadBooleanEOF();
    }

    private static void testReadBooleanNormal() throws Exception {
        File tempFile = File.createTempFile("test", ".bin");
        tempFile.deleteOnExit();
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(1); // true
        }
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "r")) {
            boolean result = raf.readBoolean();
            if (result != true) {
                throw new AssertionError("Expected true, got " + result);
            }
        }
    }

    private static void testReadBooleanEOF() throws Exception {
        File tempFile = File.createTempFile("testEOF", ".bin");
        tempFile.deleteOnExit();
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(0); // false
        }
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "r")) {
            boolean result = raf.readBoolean();
            if (result != false) {
                throw new AssertionError("Expected false, got " + result);
            }
        }
        // Test behavior when reading beyond EOF
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "r")) {
            raf.seek(1); // beyond EOF
            boolean result = raf.readBoolean();
            throw new AssertionError("Expected EOFException, but readBoolean returned " + result);
        } catch (EOFException e) {
            // expected
        }
    }
}
