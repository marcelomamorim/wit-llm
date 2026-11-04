/*
 * @test
 * @summary Verifies Formatter(String fileName, Charset charset, Locale l) writes to the file using the given charset and rejects a null charset
 * @run main FormatterWitupTest
 */

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Locale;

public class FormatterWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static File newTempFile() throws Exception {
        return File.createTempFile("formatter-witup", ".txt");
    }

    static void testBasicOutput() throws Exception {
        File f = newTempFile();
        try {
            Formatter fmt = new Formatter(f.getPath(), StandardCharsets.UTF_8, Locale.US);
            fmt.format("%s-%d", "a", 3);
            fmt.close();
            byte[] data = Files.readAllBytes(f.toPath());
            check(new String(data, StandardCharsets.UTF_8).equals("a-3"),
                  "unexpected content: " + new String(data, StandardCharsets.UTF_8));
        } finally {
            f.delete();
        }
    }

    static void testUtf8Encoding() throws Exception {
        File f = newTempFile();
        try {
            Formatter fmt = new Formatter(f.getPath(), StandardCharsets.UTF_8, Locale.US);
            fmt.format("%s", "é");
            fmt.close();
            byte[] data = Files.readAllBytes(f.toPath());
            byte[] expected = "é".getBytes(StandardCharsets.UTF_8);
            check(Arrays.equals(data, expected), "UTF-8 bytes not honored");
        } finally {
            f.delete();
        }
    }

    static void testIso88591Encoding() throws Exception {
        File f = newTempFile();
        try {
            Charset cs = StandardCharsets.ISO_8859_1;
            Formatter fmt = new Formatter(f.getPath(), cs, Locale.US);
            fmt.format("%s", "é");
            fmt.close();
            byte[] data = Files.readAllBytes(f.toPath());
            check(data.length == 1, "expected 1 byte for ISO-8859-1, got " + data.length);
            check(data[0] == (byte) 0xE9, "unexpected ISO-8859-1 byte: " + data[0]);
        } finally {
            f.delete();
        }
    }

    static void testNullCharsetThrowsNpe() throws Exception {
        File f = newTempFile();
        try {
            boolean thrown = false;
            try {
                new Formatter(f.getPath(), (Charset) null, Locale.US);
            } catch (NullPointerException e) {
                thrown = true;
            }
            check(thrown, "expected NullPointerException for null charset");
        } finally {
            f.delete();
        }
    }

    public static void main(String[] args) throws Exception {
        testBasicOutput();
        testUtf8Encoding();
        testIso88591Encoding();
        testNullCharsetThrowsNpe();
    }
}
