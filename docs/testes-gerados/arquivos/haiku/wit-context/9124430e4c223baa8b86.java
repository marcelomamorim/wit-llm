/*
 * @test
 * @summary Observable String.compareTo behavior for mixed LATIN1/UTF16 operands, which reaches StringLatin1.compareToUTF16 indirectly
 * @run main StringLatin1WitupTest
 */

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        String latin = "abc";
        String utf16 = "abc\u4e2d";

        check(latin.compareTo(utf16) < 0, "latin prefix must sort before utf16 extension");
        check(utf16.compareTo(latin) > 0, "utf16 extension must sort after latin prefix");
        check("ab".compareTo("ab\u0100") == -1, "prefix difference must be length difference");
        check("ab\u0100".compareTo("ab") == 1, "reverse prefix difference must be length difference");
        check("\u00e9".compareTo("\u0100") < 0, "latin1 char 0xE9 must sort before U+0100");
        check("\u0100".compareTo("\u00e9") > 0, "U+0100 must sort after latin1 char 0xE9");
        check("abc".compareTo("abc") == 0, "equal latin1 strings must compare equal");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
