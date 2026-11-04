/*
 * @test
 * @summary Test java.util.Formatter(File, Charset, Locale) constructor
 * @run main java.util.FormatterWitupTest
 */

import java.util.Formatter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Objects;

public class FormatterWitupTest {
    public static void main(String[] args) throws IOException {
        testNullCharsetThrowsNPE();
        testValidConstructor();
    }

    private static void testNullCharsetThrowsNPE() {
        File tmpFile = new File("FormatterWitupTest.tmp");
        try {
            Charset charset = null;
            Locale locale = Locale.US;
            try {
                new Formatter(tmpFile, charset, locale);
                throw new RuntimeException("Expected NullPointerException for null charset");
            } catch (NullPointerException npe) {
                if (!"charset".equals(npe.getMessage())) {
                    throw new RuntimeException("NullPointerException message mismatch: " + npe.getMessage());
                }
            }
        } finally {
            tmpFile.delete();
        }
    }

    private static void testValidConstructor() throws IOException {
        File tmpFile = new File("FormatterWitupTest.tmp");
        try {
            Charset charset = Charset.forName("UTF-8");
            Locale locale = Locale.US;
            Formatter formatter = new Formatter(tmpFile, charset, locale);
            if (formatter == null) {
                throw new RuntimeException("Formatter instance is null");
            }
            formatter.close();
        } finally {
            tmpFile.delete();
        }
    }
}
