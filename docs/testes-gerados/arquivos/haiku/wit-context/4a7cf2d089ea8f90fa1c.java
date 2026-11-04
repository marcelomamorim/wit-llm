/*
 * @test
 * @summary Witup-derived checks for StringLatin1.toUpperCase through the public String API
 * @run main StringLatin1WitupTest
 */

import java.util.Locale;

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        testNullLocaleThrowsNPE();
        testAsciiUpperCase();
        testAlreadyUpperCaseReturnsSameInstance();
        testNonLatin1ExpansionPath();
        testTurkishDottedI();
    }

    static void testNullLocaleThrowsNPE() {
        try {
            "abc".toUpperCase((Locale) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null locale");
    }

    static void testAsciiUpperCase() {
        String r = "abc".toUpperCase(Locale.ROOT);
        if (!"ABC".equals(r)) {
            throw new RuntimeException("unexpected result: " + r);
        }
    }

    static void testAlreadyUpperCaseReturnsSameInstance() {
        String s = "ABC";
        String r = s.toUpperCase(Locale.ROOT);
        if (r != s) {
            throw new RuntimeException("expected same instance when no change is needed");
        }
    }

    static void testNonLatin1ExpansionPath() {
        String r = "\u00ff".toUpperCase(Locale.ROOT);
        if (!"\u0178".equals(r)) {
            throw new RuntimeException("unexpected result: " + r);
        }
    }

    static void testTurkishDottedI() {
        String r = "i".toUpperCase(new Locale("tr"));
        if (!"\u0130".equals(r)) {
            throw new RuntimeException("unexpected Turkish result: " + r);
        }
    }
}
