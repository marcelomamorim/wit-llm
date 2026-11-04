/* @test    @summary Test for readChar method in RandomAccessFile to verify EOFException behavior    @run main RandomAccessFileWitupTest */

import java.io.RandomAccessFile;
import java.io.EOFException;
import java.io.IOException;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) {
        testReadCharEOF();
    }

    public static void testReadCharEOF() {
        try {
            // Cria arquivo temporário vazio para simular EOF
            String filename = "tempEOFTest.txt";
            RandomAccessFile raf = new RandomAccessFile(filename, "r");
            try {
                // Tenta ler um caractere de arquivo vazio
                raf.readChar();
                System.err.println("Erro: EOFException não foi lançada ao ler de arquivo vazio.");
            } catch (EOFException e) {
                // Esperado: EOFException deve ser lançada
                System.out.println("Passou: EOFException lançada como esperado ao ler de arquivo vazio.");
            } catch (IOException e) {
                System.err.println("Erro inesperado ao ler arquivo: " + e);
            } finally {
                raf.close();
                // Opcional: deletar arquivo temporário
                new java.io.File(filename).delete();
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar arquivo para teste: " + e);
        }
    }
}
