/*
 * @test
 * @summary Testa o método toFile() de JrtPath, que lança UnsupportedOperationException.
 * @run main JrtPathWitupTest
 */

import java.io.File;
import jdk.internal.jrtfs.JrtPath;

public class JrtPathWitupTest {

    public static void main(String[] args) {
        testToFileThrowsException();
    }

    public static void testToFileThrowsException() {
        JrtPath path = new JrtPath(null, ""); // O construtor pode ser ajustado se necessário
        try {
            path.toFile();
            System.err.println("Failed: Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            System.out.println("Passed: UnsupportedOperationException was correctly thrown.");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
            System.exit(1);
        }
    }
}
