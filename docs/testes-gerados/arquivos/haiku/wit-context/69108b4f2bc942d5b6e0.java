/*
 * @test
 * @summary Witup-derived checks for String.toUpperCase(Locale): null locale NPE on Latin1 and UTF16 paths, plus locale-sensitive results
 * @run main StringWitupTest
 */

import java.util.Locale;

public class StringWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + message);
        }
    }

    static void testNullLocaleLatin1() {
        String s = "abc";
        try {
            s.toUpperCase((Locale) null);
            check(false, "expected NullPointerException for Latin1 string with null locale");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullLocaleUtf16() {
        String s = "\u0100abc";
        try {
            s.toUpperCase((Locale) null);
            check(false, "expected NullPointerException for UTF16 string with null locale");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRootLocaleLatin1() {
        check("abc".toUpperCase(Locale.ROOT).equals("ABC"), "Latin1 toUpperCase with ROOT");
    }

    static void testRootLocaleUtf16() {
        String in = "\u0100abc";
        String expected = "\u0100ABC";
        check(in.toUpperCase(Locale.ROOT).equals(expected), "UTF16 toUpperCase with ROOT");
    }

    static void testEnglishLocale() {
        check("hello".toUpperCase(Locale.ENGLISH).equals("HELLO"), "English toUpperCase");
    }

    static void testTurkishLocale() {
        Locale tr = Locale.forLanguageTag("tr");
        check("i".toUpperCase(tr).equals("\u0130"), "Turkish dotted i uppercase");
    }

    static void testEmptyString() {
        check("".toUpperCase(Locale.ROOT).isEmpty(), "empty string uppercase");
    }

    public static void main(String[] args) {
        testNullLocaleLatin1();
        testNullLocaleUtf16();
        testRootLocaleLatin1();
        testRootLocaleUtf16();
        testEnglishLocale();
        testTurkishLocale();
        testEmptyString();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
