/*
 * @test
 * @summary Verify Scanner(Path, String) null handling and observable behavior for a valid file and charset
 * @run main ScannerWitupTest
 */
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) throws Exception {
        testValidPathAndCharset();
        testNullCharsetNameThrowsNPE();
        testNullSourceThrowsNPE();
    }

    static void testValidPathAndCharset() throws Exception {
        Path tmp = Files.createTempFile("ScannerWitupTest", ".txt");
        try {
            Files.write(tmp, "1 2 3".getBytes(StandardCharsets.UTF_8));
            try (Scanner sc = new Scanner(tmp, "UTF-8")) {
                if (sc.nextInt() != 1) {
                    throw new RuntimeException("unexpected first token");
                }
                if (sc.nextInt() != 2) {
                    throw new RuntimeException("unexpected second token");
                }
                if (sc.nextInt() != 3) {
                    throw new RuntimeException("unexpected third token");
                }
                if (sc.hasNext()) {
                    throw new RuntimeException("unexpected extra token");
                }
            }
        } finally {
            Files.deleteIfExists(tmp);
        }
    }

    static void testNullCharsetNameThrowsNPE() throws Exception {
        Path tmp = Files.createTempFile("ScannerWitupTest", ".txt");
        try {
            String nullCharset = null;
            try (Scanner sc = new Scanner(tmp, nullCharset)) {
                throw new RuntimeException("expected NullPointerException for null charsetName");
            } catch (NullPointerException expected) {
                // expected
            }
        } finally {
            Files.deleteIfExists(tmp);
        }
    }

    static void testNullSourceThrowsNPE() throws Exception {
        Path nullPath = null;
        try {
            try (Scanner sc = new Scanner(nullPath, "UTF-8")) {
                throw new RuntimeException("expected NullPointerException for null source");
            } catch (NullPointerException expected) {
                // expected
            }
        }
    }
}
