/*
 * @test
 * @summary Verifies Formatter(String, Charset, Locale) rejects a null charset with NullPointerException and writes formatted output to a file for a valid charset.
 * @run main FormatterWitupTest
 */

import java.io.File;
import java.nio.charset.Charset;
import java.util.Formatter;
import java.util.Locale;

public class FormatterWitupTest {

    public static void main(String[] args) throws Exception {
        File tmp = File.createTempFile("FormatterWitupTest", ".txt");
        try {
            testNullCharsetThrowsNpe(tmp);
            testValidCharsetWritesFile(tmp);
        } finally {
            tmp.delete();
        }
    }

    private static void testNullCharsetThrowsNpe(File tmp) throws Exception {
        boolean thrown = false;
        try {
            Formatter f = new Formatter(tmp.getPath(), (Charset) null, Locale.US);
            f.close();
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null charset");
        }
    }

    private static void testValidCharsetWritesFile(File tmp) throws Exception {
        try (Formatter f = new Formatter(tmp.getPath(), Charset.forName("UTF-8"), Locale.US)) {
            f.format("%d", 42);
        }
        if (tmp.length() == 0) {
            throw new RuntimeException("Expected non-empty output file after formatting");
        }
    }
}
