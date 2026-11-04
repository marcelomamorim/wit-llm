/*
 * @test
 * @summary Test for jdk.internal.jimage.ImageReader.getAttributes(int)
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws IOException {
        testGetAttributesWithClosedReader();
        testGetAttributesWithOpenReader();
    }

    private static void testGetAttributesWithClosedReader() {
        try {
            // Cria um mock ou uma instância de ImageReader e fecha antes de chamar getAttributes
            // Como não há construtor público, simula-se o fechamento após abrir
            // Aqui, assume-se que o método open retorna uma instância válida
            Path dummyPath = java.nio.file.Paths.get("/dev/null");
            ImageReader reader = ImageReader.open(dummyPath);
            reader.close();
            // Chamada que deve lançar IllegalStateException
            try {
                reader.getAttributes(0);
                System.err.println("Erro: esperado IllegalStateException não lançada.");
            } catch (IllegalStateException e) {
                // Esperado
                System.out.println("Passou: getAttributes lança IllegalStateException após fechamento.");
            } catch (IOException e) {
                System.err.println("Erro: IOException inesperada: " + e);
            }
        } catch (IOException e) {
            System.err.println("Erro ao abrir ou fechar o ImageReader: " + e);
        }
    }

    private static void testGetAttributesWithOpenReader() {
        try {
            Path dummyPath = java.nio.file.Paths.get("/dev/null");
            ImageReader reader = ImageReader.open(dummyPath);
            // Aqui, como o método é um stub, assume-se que retorna um array vazio ou válido
            long[] attrs = reader.getAttributes(0);
            if (attrs != null) {
                System.out.println("Passou: getAttributes retornou " + java.util.Arrays.toString(attrs));
            } else {
                System.err.println("Erro: getAttributes retornou null.");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Erro ao abrir ou fechar o ImageReader: " + e);
        }
    }
}
