/* @test    @summary Test readInt method for EOFException behavior    @run main RandomAccessFileWitupTest_P0f1a1afa5581 */

import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.EOFException;

public class RandomAccessFileWitupTest_P0f1a1afa5581 {

    public static void main(String[] args) throws Exception {
        testReadIntEOFException();
    }

    private static void testReadIntEOFException() throws IOException {
        // Cria arquivo temporário para teste
        java.io.File tempFile = java.io.File.createTempFile("testfile", null);
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "r")) {
            // Garante que o arquivo está vazio
            raf.setLength(0);
            // Tenta ler um int, esperando EOFException
            boolean exceptionThrown = false;
            try {
                raf.readInt();
            } catch (EOFException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected EOFException was not thrown.");
            }
        }
    }
}
