/*
 * @test
 * @summary Verifies String.toLowerCase(Locale) for Latin1 and UTF16 paths, locale-sensitive mapping, and null locale handling
 * @run main StringWitupTest
 */
public class StringWitupTest {
    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        check("ABC".toLowerCase(java.util.Locale.ROOT).equals("abc"),
              "Latin1 ASCII lowercase failed");
        check("".toLowerCase(java.util.Locale.ROOT).isEmpty(),
              "Empty string lowercase failed");
        check("A\u0100".toLowerCase(java.util.Locale.ROOT).equals("a\u0101"),
              "UTF16 path lowercase failed");
        check("I".toLowerCase(java.util.Locale.ROOT).equals("i"),
              "ROOT locale lowercase of I failed");
        check("I".toLowerCase(new java.util.Locale("tr")).equals("\u0131"),
              "Turkish locale lowercase of I failed");
        try {
            "ABC".toLowerCase((java.util.Locale) null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
