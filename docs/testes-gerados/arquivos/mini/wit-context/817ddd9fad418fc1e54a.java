/*
 * @test
 * @summary Test Scanner(InputStream, String) constructor for null charsetName and valid charsetName
 * @run main ScannerWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testNullCharsetNameThrowsNPE();
        testValidCharsetNameCreatesScanner();
    }

    private static void testNullCharsetNameThrowsNPE() {
        InputStream is = new ByteArrayInputStream(new byte[0]);
        try {
            new Scanner(is, null);
            throw new RuntimeException("Expected NullPointerException for null charsetName");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testValidCharsetNameCreatesScanner() {
        InputStream is = new ByteArrayInputStream("test".getBytes(Charset.forName("UTF-8")));
        Scanner scanner = null;
        try {
            scanner = new Scanner(is, "UTF-8");
            if (!scanner.hasNext()) {
                throw new RuntimeException("Scanner should have input tokens");
            }
            String token = scanner.next();
            if (!"test".equals(token)) {
                throw new RuntimeException("Expected token 'test', got: " + token);
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
