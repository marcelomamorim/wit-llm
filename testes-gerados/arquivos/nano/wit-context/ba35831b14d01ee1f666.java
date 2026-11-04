/*
 * @test
 * @summary Test for jdk.internal.jimage.ImageReader.getName()
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws IOException {
        testGetNameWhenOpen();
        testGetNameWhenClosed();
    }

    private static void testGetNameWhenOpen() throws IOException {
        Path path = Paths.get("/path/to/valid/image"); // substitua por um caminho válido se necessário
        // Abre o ImageReader
        try (jdk.internal.jimage.ImageReader reader = jdk.internal.jimage.ImageReader.open(path)) {
            String name = reader.getName();
            System.out.println("getName() when open: " + name);
        } catch (IOException e) {
            // Caso não seja possível abrir, o teste não é válido
            System.err.println("Failed to open ImageReader: " + e.getMessage());
        }
    }

    private static void testGetNameWhenClosed() {
        Path path = Paths.get("/path/to/valid/image"); // substitua por um caminho válido se necessário
        jdk.internal.jimage.ImageReader reader = null;
        try {
            reader = jdk.internal.jimage.ImageReader.open(path);
        } catch (IOException e) {
            System.err.println("Failed to open ImageReader: " + e.getMessage());
            return;
        }
        try {
            reader.close();
        } catch (IOException e) {
            System.err.println("Failed to close ImageReader: " + e.getMessage());
            return;
        }
        // Agora tenta chamar getName após fechar
        try {
            reader.getName();
            System.err.println("Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            System.out.println("Caught expected IllegalStateException after close: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Unexpected IOException: " + e.getMessage());
        }
    }
}
