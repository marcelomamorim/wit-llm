/*
 * @test
 * @summary Observable behavior of String.toUpperCase(Locale), which delegates to StringLatin1.toUpperCase for Latin1 strings
 * @run main StringLatin1WitupTest
 */

import java.util.Locale;

public class StringLatin1WitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        // Already uppercase Latin1 string: same instance is returned
        String upper = "ABC";
        check(upper.toUpperCase(Locale.ROOT) == upper, "unchanged string should be returned as same instance");

        // Simple ASCII conversion
        check("hello".toUpperCase(Locale.ROOT).equals("HELLO"), "ASCII uppercase failed");

        // Latin1 characters that stay within Latin1 after conversion
        check("\u00e9t\u00e9".toUpperCase(Locale.ROOT).equals("\u00c9T\u00c9"), "Latin1 uppercase failed");

        // Mixed: some chars already upper, some need change
        check("aBc".toUpperCase(Locale.ROOT).equals("ABC"), "mixed-case conversion failed");

        // Result leaves Latin1 range: y-diaeresis uppercases to U+0178
        check("\u00ff".toUpperCase(Locale.ROOT).equals("\u0178"), "y-diaeresis should uppercase to U+0178");

        // Result expands to two characters: sharp s uppercases to "SS"
        check("\u00df".toUpperCase(Locale.ROOT).equals("SS"), "sharp s should uppercase to SS");

        // Turkish locale: dotless/dotted i handling
        check("i".toUpperCase(new Locale("tr")).equals("\u0130"), "Turkish i should uppercase to U+0130");

        // Empty string
        check("".toUpperCase(Locale.ROOT).isEmpty(), "empty string uppercase failed");

        // Null locale must be rejected
        boolean npe = false;
        try {
            "abc".toUpperCase((Locale) null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null locale should throw NullPointerException");
    }
}
