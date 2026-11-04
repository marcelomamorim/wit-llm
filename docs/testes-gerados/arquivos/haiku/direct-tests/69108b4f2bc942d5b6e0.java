/*
 * @test
 * @summary Checks String.toUpperCase(Locale) for ASCII, locale-sensitive, non-BMP, empty and null-locale inputs
 * @run main StringWitupTest
 */

import java.util.Locale;

public class StringWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAsciiRoot() {
        check("abc".toUpperCase(Locale.ROOT).equals("ABC"), "ASCII ROOT upper failed");
        check("Hello World 123".toUpperCase(Locale.ROOT).equals("HELLO WORLD 123"), "mixed ASCII upper failed");
    }

    static void testEmptyString() {
        check("".toUpperCase(Locale.ROOT).isEmpty(), "empty string upper failed");
    }

    static void testLatin1Accents() {
        check("\u00e9t\u00e9".toUpperCase(Locale.ROOT).equals("\u00c9T\u00c9"), "Latin1 accented upper failed");
    }

    static void testTurkishLocale() {
        Locale tr = new Locale("tr");
        check("i".toUpperCase(tr).equals("\u0130"), "Turkish dotted I upper failed");
        check("i".toUpperCase(Locale.ROOT).equals("I"), "ROOT i upper failed");
    }

    static void testNonBmp() {
        String lower = "\uD801\uDC28";
        String upper = "\uD801\uDC00";
        check(lower.toUpperCase(Locale.ROOT).equals(upper), "non-BMP upper failed");
    }

    static void testNullLocale() {
        boolean thrown = false;
        try {
            "abc".toUpperCase((Locale) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null locale should throw NullPointerException");
    }

    public static void main(String[] args) {
        testAsciiRoot();
        testEmptyString();
        testLatin1Accents();
        testTurkishLocale();
        testNonBmp();
        testNullLocale();
    }
}
