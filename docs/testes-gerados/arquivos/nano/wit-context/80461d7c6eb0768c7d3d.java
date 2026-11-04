/*
 * @test
 * @summary Tests for java.nio.file.Files.createLink(Path, Path)
 * @run main FilesWitupTest
 */

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

public class FilesWitupTest {
    public static void main(String[] args) throws Exception {
        testCreateLinkUnsupportedOperation();
    }

    private static void testCreateLinkUnsupportedOperation() {
        Path link = Paths.get("/tmp/link");
        Path existing = Paths.get("/tmp/existing");
        try {
            Path result = Files.createLink(link, existing);
            // Se não lançar exceção, o teste falha
            System.err.println("Expected UnsupportedOperationException, but got result: " + result);
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Esperado
            System.out.println("Caught expected UnsupportedOperationException");
        } catch (IOException e) {
            // Se lançar IOException, o teste falha
            System.err.println("Unexpected IOException: " + e);
            System.exit(1);
        } catch (Exception e) {
            // Outros exceptions também falham
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
