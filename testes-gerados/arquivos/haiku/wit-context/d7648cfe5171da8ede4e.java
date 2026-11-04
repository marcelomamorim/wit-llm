/*
 * @test
 * @summary Verifies String.toLowerCase(Locale) for Latin1 and UTF16 strings, locale-sensitive mapping, and NullPointerException for a null locale
 * @run main StringWitupTest
 */

import java.util.Locale;

public class StringWitupTest {

    public static void main(String[] args) {
        testBasicLatin1();
        testEmptyString();
        testUtf16();
        testLocaleSensitive();
        testNullLocaleLatin1();
        testNullLocaleUtf16();
    }

    static void testBasicLatin1() {
        check("ABC".toLowerCase(Locale.ROOT), "abc", "latin1 root");
        check("AbC123".toLowerCase(Locale.ENGLISH), "abc123", "latin1 english");
    }

    static void testEmptyString() {
        check("".toLowerCase(Locale.ROOT), "", "empty string");
    }

    static void testUtf16() {
        String s = "\u0391\u0392\u0393";
        check(s.toLowerCase(Locale.ROOT), "\u03b1\u03b2\u03b3", "greek utf16");
    }

    static void testLocaleSensitive() {
        Locale tr = new Locale("tr");
        check("I".toLowerCase(tr), "\u0131", "turkish dotless i");
        check("I".toLowerCase(Locale.ROOT), "i", "root i");
    }

    static void testNullLocaleLatin1() {
        expectNpe("ABC", "latin1 null locale");
    }

    static void testNullLocaleUtf16() {
        expectNpe("\u0391\u0392", "utf16 null locale");
    }

    static void expectNpe(String s, String label) {
        try {
            s.toLowerCase((Locale) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException: " + label);
    }

    static void check(String actual, String expected, String label) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(label + ": expected <" + expected + "> but was <" + actual + ">");
        }
    }
}
