/*
 * @test
 * @summary Test the locale() method of java.util.Formatter for correct return of locale.
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.util.Locale;

public class FormatterWitupTest {

    public static void main(String[] args) {
        testDefaultLocale();
        testCustomLocale();
    }

    public static void testDefaultLocale() {
        Formatter formatter = new Formatter();
        Locale locale = formatter.locale();
        assert locale != null : "Locale should not be null";
        // The default locale should be the JVM default locale
        assert locale.equals(Locale.getDefault()) : "Default locale mismatch";
        formatter.close();
    }

    public static void testCustomLocale() {
        Locale customLocale = Locale.FRANCE;
        Formatter formatter = new Formatter(Locale.FRANCE);
        Locale locale = formatter.locale();
        assert locale.equals(customLocale) : "Locale should be FRANCE";
        formatter.close();
    }
}
