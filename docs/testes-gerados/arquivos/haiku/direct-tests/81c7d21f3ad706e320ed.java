/*
 * @test
 * @summary Tests Character.toChars for BMP, supplementary and invalid code points
 * @run main CharacterWitupTest
 */

public class CharacterWitupTest {

    public static void main(String[] args) {
        char[] a = Character.toChars(0x41);
        check(a.length == 1 && a[0] == 'A', "BMP 'A' expected");

        char[] max = Character.toChars(0xFFFF);
        check(max.length == 1 && max[0] == (char) 0xFFFF, "BMP max expected");

        char[] surrogateCodePoint = Character.toChars(0xD800);
        check(surrogateCodePoint.length == 1 && surrogateCodePoint[0] == (char) 0xD800,
              "surrogate code point is BMP and must yield one char");

        char[] first = Character.toChars(0x10000);
        check(first.length == 2 && first[0] == (char) 0xD800 && first[1] == (char) 0xDC00,
              "U+10000 surrogate pair expected");

        char[] emoji = Character.toChars(0x1F600);
        check(emoji.length == 2 && emoji[0] == (char) 0xD83D && emoji[1] == (char) 0xDE00,
              "U+1F600 surrogate pair expected");

        char[] last = Character.toChars(0x10FFFF);
        check(last.length == 2 && last[0] == (char) 0xDBFF && last[1] == (char) 0xDFFF,
              "U+10FFFF surrogate pair expected");

        char[] b1 = Character.toChars(0x41);
        check(a != b1, "toChars must return a new array on each call");

        expectIAE(0x110000, "Not a valid Unicode code point: 0x110000");
        expectIAE(-1, "Not a valid Unicode code point: 0xFFFFFFFF");
    }

    static void expectIAE(int codePoint, String expectedMessage) {
        try {
            Character.toChars(codePoint);
        } catch (IllegalArgumentException e) {
            check(expectedMessage.equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for " + codePoint);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
