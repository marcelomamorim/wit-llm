/* @test    @summary Test for RandomAccessFile.readByte() method to verify behavior at EOF    @run main RandomAccessFileWitupTest */

import java.io.RandomAccessFile;
import java.io.IOException;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws IOException {
        testReadByteAtEOF();
    }

    private static void testReadByteAtEOF() {
        try {
            // Cria arquivo temporário com conteúdo de um byte
            java.io.File tempFile = java.io.File.createTempFile("testfile", null);
            tempFile.deleteOnExit();
            try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
                // Escreve um byte
                raf.writeByte(0x01);
                // Posiciona no final do arquivo
                raf.seek(raf.length());
                // Tenta ler além do EOF
                int result = raf.readByte();
                // Se chegou aqui, o método não lançou EOFException, o que é inesperado
                System.err.println("Erro: Esperado EOFException, mas readByte retornou: " + result);
            } catch (IOException e) {
                if (e instanceof java.io.EOFException) {
                    // Exceção esperada
                    System.out.println("EOFException lançada como esperado ao ler além do EOF.");
                } else {
                    System.err.println("Erro inesperado ao ler além do EOF: " + e);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar arquivo temporário: " + e);
        }
    }
}
