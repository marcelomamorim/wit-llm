/*
 * @test
 * @summary Test for Formatter constructor with filename, charset, and locale.
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.nio.charset.Charset;
import java.util.Locale;
import java.io.IOException;

public class FormatterWitupTest {

    public static void main(String[] args) throws IOException {
        testConstructorWithNullCharset();
        testConstructorWithValidParams();
    }

    private static void testConstructorWithNullCharset() {
        String filename = "testfile.txt";
        Charset charset = null;
        Locale locale = Locale.US;
        try {
            new Formatter(filename, charset, locale);
            System.out.println("FAIL: Expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null charset");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testConstructorWithValidParams() {
        String filename = "testfile.txt";
        Charset charset = Charset.forName("UTF-8");
        Locale locale = Locale.US;
        try {
            Formatter formatter = new Formatter(filename, charset, locale);
            if (formatter != null) {
                System.out.println("PASS: Formatter constructed successfully with valid params");
                formatter.close();
            } else {
                System.out.println("FAIL: Formatter is null");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
