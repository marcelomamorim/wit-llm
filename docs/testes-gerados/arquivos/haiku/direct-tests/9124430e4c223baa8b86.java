/*
 * @test
 * @summary Mixed-coder String.compareTo paths that reach StringLatin1.compareToUTF16 through the public API
 * @run main StringLatin1WitupTest
 */

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        check("a".compareTo("\u0100") == 'a' - 0x100, "latin1 char vs utf16 char");
        check("\u0100".compareTo("a") == 0x100 - 'a', "utf16 char vs latin1 char");
        check("ab".compareTo("a\u0100") == 'b' - 0x100, "second char mismatch");
        check("a".compareTo("a\u0100") == -1, "latin1 prefix shorter");
        check("a\u0100".compareTo("a") == 1, "utf16 longer than latin1 prefix");
        check("\u00ff".compareTo("\u0100") == 0xff - 0x100, "max latin1 vs first utf16");
        check("\u0100".compareTo("\u0100a") == -1, "utf16 prefix shorter");
        check("\u0100a".compareTo("\u0100a") == 0, "equal utf16 strings");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
