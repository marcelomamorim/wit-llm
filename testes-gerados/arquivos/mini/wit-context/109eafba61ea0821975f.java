/*
 * @test
 * @summary Test Scanner(InputStream, Charset) constructor for null charset and normal usage
 * @run main ScannerWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testNullCharsetThrowsNPE();
        testValidCharsetCreatesScanner();
    }

    private static void testNullCharsetThrowsNPE() {
        InputStream is = new ByteArrayInputStream(new byte[0]);
        try {
            new Scanner(is, null);
            throw new RuntimeException("Expected NullPointerException for null charset");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testValidCharsetCreatesScanner() {
        InputStream is = new ByteArrayInputStream("test input".getBytes(StandardCharsets.UTF_8));
        Charset charset = StandardCharsets.UTF_8;
        Scanner scanner = null;
        try {
            scanner = new Scanner(is, charset);
            if (!scanner.hasNext()) {
                throw new RuntimeException("Scanner should have tokens for non-empty input");
            }
            String token = scanner.next();
            if (!"test".equals(token)) {
                throw new RuntimeException("Expected first token to be 'test', got: " + token);
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
