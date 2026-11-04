/*
 * @test
 * @summary Witup-style checks for Formatter(File, Charset, Locale)
 * @run main FormatterWitupTest
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Locale;

public class FormatterWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCharsetThrowsNPE();
        testWritesWithGivenCharset();
        testMissingDirectoryThrowsFileNotFound();
    }

    static void testNullCharsetThrowsNPE() throws Exception {
        File f = File.createTempFile("fmtwitup", ".txt");
        try {
            new Formatter(f, null, Locale.US);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            f.delete();
        }
    }

    static void testWritesWithGivenCharset() throws Exception {
        File f = File.createTempFile("fmtwitup", ".txt");
        try {
            Formatter fm = new Formatter(f, StandardCharsets.UTF_8, Locale.US);
            fm.format("%s-%s-%d", "a", "é", 7);
            fm.close();
            String got = new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8);
            if (!"a-é-7".equals(got)) {
                throw new RuntimeException("unexpected content: " + got);
            }
        } finally {
            f.delete();
        }
    }

    static void testMissingDirectoryThrowsFileNotFound() throws Exception {
        File dir = new File(System.getProperty("java.io.tmpdir"),
                "no-such-dir-" + System.nanoTime());
        File f = new File(dir, "x.txt");
        try {
            new Formatter(f, StandardCharsets.UTF_8, Locale.US);
            throw new RuntimeException("expected FileNotFoundException");
        } catch (FileNotFoundException expected) {
            // expected
        }
    }
}
