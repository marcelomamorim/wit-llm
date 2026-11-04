/*
 * @test
 * @summary Test Formatter constructor with File, Charset, and Locale parameters
 * @run main FormatterWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

public class FormatterWitupTest {

    public static void main(String[] args) {
        testFormatterConstructorWithFileCharsetLocale();
    }

    public static void testFormatterConstructorWithFileCharsetLocale() {
        try {
            // Create a temporary file for testing
            File tempFile = File.createTempFile("test", ".txt");
            tempFile.deleteOnExit();

            // Use a valid charset and locale
            Charset charset = Charset.forName("UTF-8");
            Locale locale = Locale.US;

            // Instantiate Formatter with file, charset, and locale
            java.util.Formatter formatter = new java.util.Formatter(tempFile, charset, locale);
            // Verify that formatter is not null
            if (formatter == null) {
                throw new AssertionError("Formatter instance is null");
            }
            // Close formatter to release resources
            formatter.close();
        } catch (IOException e) {
            throw new AssertionError("IOException during test: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e.getMessage(), e);
        }
    }
}
