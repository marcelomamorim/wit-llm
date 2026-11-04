/*
 * @test
 * @summary Verifies Locale.getDisplayScript(Locale) for empty script, explicit script, determinism and null inLocale handling
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testNoScriptReturnsEmpty();
        testScriptReturnsDisplayName();
        testRepeatedCallsAreDeterministic();
        testNullInLocaleThrowsNPE();
    }

    static void testNoScriptReturnsEmpty() {
        String s = Locale.US.getDisplayScript(Locale.US);
        if (s == null || !s.isEmpty()) {
            throw new RuntimeException("Expected empty script name, got: " + s);
        }
    }

    static void testScriptReturnsDisplayName() {
        Locale l = Locale.forLanguageTag("en-Latn");
        String s = l.getDisplayScript(Locale.US);
        if (!"Latin".equals(s)) {
            throw new RuntimeException("Expected 'Latin', got: " + s);
        }
    }

    static void testRepeatedCallsAreDeterministic() {
        Locale l = Locale.forLanguageTag("en-Latn");
        String first = l.getDisplayScript(Locale.US);
        String second = l.getDisplayScript(Locale.US);
        if (first == null || !first.equals(second)) {
            throw new RuntimeException("Non-deterministic result: " + first + " vs " + second);
        }
    }

    static void testNullInLocaleThrowsNPE() {
        Locale l = Locale.forLanguageTag("en-Latn");
        try {
            l.getDisplayScript(null);
            throw new RuntimeException("Expected NullPointerException for null inLocale");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
