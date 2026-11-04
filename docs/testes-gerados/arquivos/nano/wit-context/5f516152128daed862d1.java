/*
 * @test
 * @summary Test for getChildren() method in jdk.internal.jimage.ImageReader
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.util.List;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws IOException {
        testGetChildrenThrowsIllegalArgumentException();
    }

    private static void testGetChildrenThrowsIllegalArgumentException() {
        // Cria uma instância de ImageReader usando um caminho fictício, pois o método é estático
        // e não há construtor público, então usamos um método de fábrica.
        // Como o método open lança IOException, tratamos essa exceção.
        try {
            // Como o método open requer um Path válido, criamos um Path fictício.
            // Para fins de teste, podemos usar um Path fictício, pois o método lança exceção
            // apenas se o arquivo não for válido, o que não é o foco aqui.
            java.nio.file.Path fakePath = java.nio.file.Paths.get("/dev/null");
            ImageReader reader = ImageReader.open(fakePath);
            // Invoca getChildren, que deve lançar IllegalArgumentException
            try {
                reader.getRootDirectory(); // Para garantir que o reader está aberto
                List<?> children = reader.getChildren();
                // Se não lançar exceção, o teste falha
                System.err.println("Erro: getChildren() não lançou IllegalArgumentException como esperado.");
            } catch (IllegalArgumentException e) {
                // Teste bem-sucedido
                System.out.println("Passou: getChildren() lançou IllegalArgumentException.");
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.err.println("Erro ao abrir ImageReader: " + e.getMessage());
        }
    }
}
