/*
 * @test
 * @summary Tests String.codePointBefore(int) for Latin1 and UTF16 strings, surrogate pairs and out-of-range indexes
 * @run main StringWitupTest
 */

public class StringWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    static boolean throwsSIOOBE(String s, int index) {
        try {
            s.codePointBefore(index);
            return false;
        } catch (StringIndexOutOfBoundsException e) {
            return true;
        }
    }

    public static void main(String[] args) {
        check("abc".codePointBefore(1) == 'a', "abc before 1 should be 'a'");
        check("abc".codePointBefore(2) == 'b', "abc before 2 should be 'b'");
        check("abc".codePointBefore(3) == 'c', "abc before 3 should be 'c'");

        check("\u00ff".codePointBefore(1) == 0xff, "Latin1 0xff should be returned unmasked");

        String emoji = "a\uD83D\uDE00";
        check(emoji.codePointBefore(3) == 0x1F600, "surrogate pair should yield U+1F600");
        check(emoji.codePointBefore(1) == 'a', "char before index 1 should be 'a'");

        check("\uDE00x".codePointBefore(1) == 0xDE00, "lone low surrogate should be returned as is");

        check(throwsSIOOBE("abc", 0), "index 0 should throw StringIndexOutOfBoundsException");
        check(throwsSIOOBE("abc", 4), "index beyond length should throw StringIndexOutOfBoundsException");
        check(throwsSIOOBE("", 0), "empty string index 0 should throw StringIndexOutOfBoundsException");
        check(throwsSIOOBE("abc", -1), "negative index should throw StringIndexOutOfBoundsException");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
