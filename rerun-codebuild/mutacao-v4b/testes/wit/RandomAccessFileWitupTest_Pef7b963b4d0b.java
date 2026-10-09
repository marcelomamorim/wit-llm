/* @test
   @summary Testa o método readInt() de RandomAccessFile para comportamento normal e EOF.
   @run main RandomAccessFileWitupTest_Pef7b963b4d0b
 */
import java.io.File;
import java.io.RandomAccessFile;
import java.io.EOFException;
import java.io.IOException;

public class RandomAccessFileWitupTest_Pef7b963b4d0b {
    public static void main(String[] args) throws Exception {
        testReadIntNormal();
        testReadIntEOF();
    }

    private static void testReadIntNormal() throws Exception {
        File tempFile = File.createTempFile("test", ".bin");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.writeInt(0x01020304);
            raf.seek(0);
            int value = raf.readInt();
            if (value != 0x01020304) {
                throw new AssertionError("Expected 0x01020304, got " + Integer.toHexString(value));
            }
        }
    }

    private static void testReadIntEOF() throws Exception {
        File tempFile = File.createTempFile("testEOF", ".bin");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "r")) {
            // Escreve apenas 2 bytes para forçar EOF ao ler um int
            raf.write(new byte[] { 0x01, 0x02 });
            raf.seek(0);
            try {
                raf.readInt();
                throw new AssertionError("Expected EOFException");
            } catch (EOFException e) {
                // esperado
            }
        }
    }
}
