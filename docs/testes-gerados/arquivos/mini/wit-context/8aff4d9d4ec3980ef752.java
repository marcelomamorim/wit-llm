/*
 * @test
 * @summary Test java.util.Formatter constructor Formatter(String, Charset, Locale) for basic behavior and null charset
 * @run main java.util.FormatterWitupTest
 */

import java.util.Formatter;
import java.util.Locale;
import java.nio.charset.Charset;
import java.io.File;
import java.io.IOException;

public class FormatterWitupTest {
    public static void main(String[] args) throws IOException {
        testValidConstructor();
        testNullCharsetThrowsNPE();
    }

    private static void testValidConstructor() throws IOException {
        // Use a valid charset and locale, and a temporary file path
        Charset charset = Charset.forName("UTF-8");
        Locale locale = Locale.US;
        File tempFile = File.createTempFile("FormatterWitupTest", ".txt");
        tempFile.deleteOnExit();

        Formatter formatter = null;
        try {
            formatter = new Formatter(tempFile.getPath(), charset, locale);
            if (formatter == null) {
                throw new AssertionError("Formatter constructor returned null");
            }
        } finally {
            if (formatter != null) {
                formatter.close();
            }
        }
    }

    private static void testNullCharsetThrowsNPE() {
        try {
            new Formatter("somefile.txt", (Charset) null, Locale.US);
            throw new AssertionError("Expected NullPointerException for null charset");
        } catch (NullPointerException npe) {
            // Expected exception
        } catch (IOException ioe) {
            throw new AssertionError("Unexpected IOException", ioe);
        }
    }
}
