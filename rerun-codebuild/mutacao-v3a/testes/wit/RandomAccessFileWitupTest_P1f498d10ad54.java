/*
 * @test
 * @summary Test for readByte() method of RandomAccessFile
 * @run main RandomAccessFileWitupTest_P1f498d10ad54
 */

import java.io.RandomAccessFile;
import java.io.IOException;

public class RandomAccessFileWitupTest_P1f498d10ad54 {

    public static void main(String[] args) throws IOException {
        testReadByteNormal();
        testReadByteEOF();
    }

    static void testReadByteNormal() {
        try {
            // Cria arquivo temporário com conteúdo
            java.io.File tempFile = java.io.File.createTempFile("test", ".txt");
            tempFile.deleteOnExit();
            try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(tempFile, "rw")) {
                raf.write(new byte[] {65, 66, 67}); // A, B, C
                raf.seek(0);
                byte b1 = raf.readByte();
                byte b2 = raf.readByte();
                byte b3 = raf.readByte();
                if (b1 != 65 || b2 != 66 || b3 != 67) {
                    throw new AssertionError("Unexpected bytes read: " + b1 + ", " + b2 + ", " + b3);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed in testReadByteNormal", e);
        }
    }

    static void testReadByteEOF() {
        try {
            java.io.File tempFile = java.io.File.createTempFile("test", ".txt");
            tempFile.deleteOnExit();
            try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(tempFile, "r")) {
                raf.readByte(); // deve lançar EOFException, arquivo vazio
                throw new AssertionError("Expected EOFException was not thrown");
            }
        } catch (IOException e) {
            if (e.getClass().getName().equals("java.io.EOFException")) {
                // esperado
            } else {
                throw new RuntimeException("Unexpected exception in testReadByteEOF", e);
            }
        }
    }
}
