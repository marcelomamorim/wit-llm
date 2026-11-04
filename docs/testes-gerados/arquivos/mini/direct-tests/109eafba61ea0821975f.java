/*
 * @test
 * @summary Test java.util.Scanner constructor Scanner(InputStream, Charset)
 * @run main java.util.ScannerWitupTest
 */

import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithValidInputStreamAndCharset();
        testConstructorWithNullInputStream();
    }

    private static void testConstructorWithValidInputStreamAndCharset() {
        String input = "Hello World";
        InputStream is = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        Charset charset = StandardCharsets.UTF_8;
        Scanner scanner = new Scanner(is, charset);
        String token = scanner.next();
        if (!"Hello".equals(token)) {
            throw new AssertionError("Expected token 'Hello' but got '" + token + "'");
        }
        scanner.close();
    }

    private static void testConstructorWithNullInputStream() {
        try {
            new Scanner(null, StandardCharsets.UTF_8);
            throw new AssertionError("Expected NullPointerException for null InputStream");
        } catch (NullPointerException npe) {
            if (!"source".equals(npe.getMessage())) {
                throw new AssertionError("Expected NullPointerException with message 'source' but got '" + npe.getMessage() + "'");
            }
        }
    }
}
