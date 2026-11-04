/*
 * @test
 * @summary Test java.util.Formatter(File, Charset, Locale) constructor for null charset and normal usage
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Locale;
import java.io.IOException;

public class FormatterWitupTest {
    public static void main(String[] args) throws IOException {
        testNullCharsetThrowsNPE();
        testValidConstructor();
    }

    private static void testNullCharsetThrowsNPE() {
        File tmpFile = new File("FormatterWitupTest.tmp");
        Locale locale = Locale.US;
        try {
            new Formatter(tmpFile, null, locale);
            throw new AssertionError("Expected NullPointerException for null charset");
        } catch (NullPointerException npe) {
            // Expected exception
        } catch (IOException ioe) {
            throw new AssertionError("Unexpected IOException: " + ioe);
        }
    }

    private static void testValidConstructor() throws IOException {
        File tmpFile = new File("FormatterWitupTest.tmp");
        Charset charset = Charset.defaultCharset();
        Locale locale = Locale.US;
        Formatter formatter = null;
        try {
            formatter = new Formatter(tmpFile, charset, locale);
            if (formatter == null) {
                throw new AssertionError("Formatter constructor returned null");
            }
            // Write a simple formatted string and flush
            formatter.format(locale, "%s %d", "test", 123);
            formatter.flush();
        } finally {
            if (formatter != null) {
                formatter.close();
            }
            // Attempt to delete the temporary file
            tmpFile.delete();
        }
    }
}
