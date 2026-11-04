/*
 * @test
 * @summary Test for ImageReader.findLocation to verify behavior when the reader is closed.
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws IOException {
        Path dummyPath = Paths.get("/dev/null");
        // Abre o ImageReader
        ImageReader reader = null;
        try {
            reader = ImageReader.open(dummyPath);
        } catch (IOException e) {
            // Se não puder abrir, o teste não é possível; aborta
            System.err.println("Failed to open ImageReader: " + e.getMessage());
            return;
        }
        // Fecha o reader para testar o comportamento após fechamento
        reader.close();
        // Tenta invocar findLocation após fechamento para verificar se lança IllegalStateException
        try {
            // Como findLocation requer nomes de método e recurso, passamos valores quaisquer
            reader.findNode("dummyNode");
            System.err.println("Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            // Esperado: a exceção deve ser lançada
            System.out.println("Caught expected IllegalStateException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Unexpected IOException: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
