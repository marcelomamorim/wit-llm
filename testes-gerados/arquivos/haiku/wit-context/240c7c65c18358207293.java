/*
 * @test
 * @summary Witup-derived checks for index validation and code point counting of non-Latin1 strings via public String API
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void expectSIOOBE(String s, int begin, int end) {
        try {
            s.codePointCount(begin, end);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for " +
                                       begin + "," + end);
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        // UTF16 string: U+4E2D, surrogate pair U+1F600, 'a' -> 4 chars, 3 code points
        String s = "\u4e2d\ud83d\ude00a";

        check(s.length() == 4, "unexpected length");
        check(s.codePointCount(0, 4) == 3, "full range count");
        check(s.codePointCount(1, 3) == 1, "surrogate pair must count as one");
        check(s.codePointCount(3, 4) == 1, "trailing char count");
        check(s.codePointCount(0, 0) == 0, "empty range count");
        check(s.codePointCount(2, 2) == 0, "empty range at surrogate count");

        expectSIOOBE(s, -1, 2);
        expectSIOOBE(s, 0, 5);
        expectSIOOBE(s, 3, 2);
    }
}
