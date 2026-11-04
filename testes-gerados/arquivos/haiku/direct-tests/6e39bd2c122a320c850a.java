/*
 * @test
 * @summary Verifies Formatter(File, Charset, Locale) writes formatted output to the file and rejects a null charset
 * @run main FormatterWitupTest
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Formatter;
import java.util.Locale;

public class FormatterWitupTest {

    public static void main(String[] args) throws Exception {
        Charset utf8 = Charset.forName("UTF-8");

        check("42".equals(writeAndRead("%d", 42, utf8)), "plain %d output");
        check("1,234,567".equals(writeAndRead("%,d", 1234567L, utf8)), "grouping with Locale.US");

        File g = File.createTempFile("formatter-witup-null", ".txt");
        try {
            new Formatter(g, null, Locale.US);
            throw new RuntimeException("expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            check("charset".equals(e.getMessage()), "NPE message should be 'charset' but was: " + e.getMessage());
        } finally {
            g.delete();
        }
    }

    private static String writeAndRead(String format, Object arg, Charset cs) throws IOException {
        File f = File.createTempFile("formatter-witup", ".txt");
        try {
            Formatter fmt = new Formatter(f, cs, Locale.US);
            fmt.format(format, arg);
            fmt.close();
            return readAll(f, cs);
        } finally {
            f.delete();
        }
    }

    private static String readAll(File f, Charset cs) throws IOException {
        try (FileInputStream in = new FileInputStream(f)) {
            byte[] bytes = in.readAllBytes();
            return new String(bytes, cs);
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
