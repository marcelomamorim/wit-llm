/*
 * @test
 * @summary Testando o método readFully(byte[] b) de RandomAccessFile
 * @run main RandomAccessFileWitupTest
 */

import java.io.RandomAccessFile;
import java.io.IOException;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) {
        testReadFullyNormal();
        testReadFullyEOF();
    }

    private static void testReadFullyNormal() {
        byte[] buffer = new byte[10];
        try {
            // Cria arquivo temporário com conteúdo
            java.io.File tempFile = java.io.File.createTempFile("test", null);
            tempFile.deleteOnExit();
            try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
                raf.write(new byte[] {1,2,3,4,5,6,7,8,9,10});
                raf.seek(0);
                raf.readFully(buffer);
                boolean correct = true;
                for (int i = 0; i < buffer.length; i++) {
                    if (buffer[i] != i + 1) {
                        correct = false;
                        break;
                    }
                }
                if (!correct) {
                    throw new AssertionError("readFully não leu os bytes corretamente");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void testReadFullyEOF() {
        byte[] buffer = new byte[10];
        try {
            java.io.File tempFile = java.io.File.createTempFile("testEOF", null);
            tempFile.deleteOnExit();
            try (RandomAccessFile raf = new RandomAccessFile(tempFile, "r")) {
                // arquivo vazio
                raf.readFully(buffer);
                throw new AssertionError("Esperado EOFException, mas não ocorreu");
            } catch (IOException e) {
                if (!(e instanceof java.io.EOFException)) {
                    throw new AssertionError("Esperado EOFException, mas ocorreu: " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
