/* @test
   @summary Testa o método readUnsignedByte() de RandomAccessFile.
   @run main RandomAccessFileWitupTest_Pbf80af80b9ce
 */
import java.io.RandomAccessFile;
import java.io.IOException;
public class RandomAccessFileWitupTest_Pbf80af80b9ce {
    public static void main(String[] args) throws Exception {
        testReadUnsignedByteNormal();
        testReadUnsignedByteEOF();
    }

    private static void testReadUnsignedByteNormal() throws IOException {
        // Cria arquivo temporário com conteúdo conhecido
        String filename = "tempfile.bin";
        try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(filename, "rw")) {
            raf.writeByte(42); // valor 42
            raf.seek(0);
            int result = raf.readUnsignedByte();
            if (result != 42) {
                throw new AssertionError("Expected 42, got " + result);
            }
        }
        // Deleta arquivo após teste
        new java.io.File(filename).delete();
    }

    private static void testReadUnsignedByteEOF() throws IOException {
        // Cria arquivo vazio
        String filename = "emptyfile.bin";
        try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(filename, "rw")) {
            raf.setLength(0);
            raf.seek(0);
            try {
                int result = raf.readUnsignedByte();
                throw new AssertionError("Expected EOFException, but got result: " + result);
            } catch (java.io.EOFException e) {
                // esperado
            }
        }
        new java.io.File(filename).delete();
    }
}
