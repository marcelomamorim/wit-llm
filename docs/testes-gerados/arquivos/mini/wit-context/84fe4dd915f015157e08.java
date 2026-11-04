/*
 * @test
 * @summary Test java.util.Scanner constructor Scanner(Path, String) for null charsetName and valid inputs
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class ScannerWitupTest {
    public static void main(String[] args) throws IOException {
        testNullCharsetNameThrowsNPE();
        testValidPathAndCharset();
    }

    private static void testNullCharsetNameThrowsNPE() {
        Path path = Paths.get("/tmp");
        try {
            new Scanner(path, null);
            throw new AssertionError("Expected NullPointerException for null charsetName");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IOException ioe) {
            throw new AssertionError("Unexpected IOException", ioe);
        }
    }

    private static void testValidPathAndCharset() throws IOException {
        Path path = Paths.get("/tmp");
        // Use a valid charset name
        Scanner scanner = null;
        try {
            scanner = new Scanner(path, "UTF-8");
            if (scanner == null) {
                throw new AssertionError("Scanner instance is null");
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
