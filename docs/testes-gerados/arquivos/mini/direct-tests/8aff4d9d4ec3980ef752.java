/*
 * @test
 * @summary Test java.util.Formatter constructor Formatter(String, Charset, Locale)
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.nio.charset.Charset;
import java.util.Locale;
import java.io.File;
import java.io.IOException;

public class FormatterWitupTest {
    public static void main(String[] args) throws IOException {
        testValidConstructor();
        testNullCharset();
    }

    private static void testValidConstructor() throws IOException {
        // Use a temporary file path
        File tempFile = File.createTempFile("FormatterWitupTest", ".txt");
        tempFile.deleteOnExit();

        Charset charset = Charset.forName("UTF-8");
        Locale locale = Locale.US;

        Formatter formatter = new Formatter(tempFile.getAbsolutePath(), charset, locale);
        if (formatter == null) {
            throw new AssertionError("Formatter instance is null");
        }
        formatter.close();
    }

    private static void testNullCharset() {
        try {
            new Formatter("dummy.txt", null, Locale.US);
            throw new AssertionError("Expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            if (!"charset".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + e.getMessage());
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
