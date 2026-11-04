/*
 * @test
 * @summary Checks observable behavior of Formatter.format(String, Object...) using the constructor locale
 * @run main FormatterWitupTest
 */
import java.util.Formatter;
import java.util.Locale;

public class FormatterWitupTest {

    public static void main(String[] args) {
        testReturnsSameInstance();
        testPlainTextWithoutArguments();
        testVarargsFormatting();
        testUsesConstructorLocale();
        testAppendsToAppendable();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReturnsSameInstance() {
        Formatter f = new Formatter(new StringBuilder(), Locale.US);
        Formatter result = f.format("%d", 42);
        check(result == f, "format must return this formatter");
    }

    static void testPlainTextWithoutArguments() {
        StringBuilder sb = new StringBuilder();
        new Formatter(sb, Locale.US).format("hello");
        check(sb.toString().equals("hello"), "plain text was not copied: " + sb);
    }

    static void testVarargsFormatting() {
        StringBuilder sb = new StringBuilder();
        new Formatter(sb, Locale.US).format("%s-%s", "a", "b");
        check(sb.toString().equals("a-b"), "unexpected output: " + sb);
    }

    static void testUsesConstructorLocale() {
        StringBuilder sb = new StringBuilder();
        new Formatter(sb, Locale.US).format("%,d", 1234567);
        check(sb.toString().equals("1,234,567"), "locale grouping not applied: " + sb);
    }

    static void testAppendsToAppendable() {
        StringBuilder sb = new StringBuilder("x=");
        new Formatter(sb, Locale.US).format("%.2f", 3.14159);
        check(sb.toString().equals("x=3.14"), "unexpected appended output: " + sb);
    }
}
