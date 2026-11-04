/*
 * @test
 * @summary Checks public StringBuilder.codePointCount, which reaches StringUTF16.codePointCountSB for UTF16-backed builders
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        // Latin1-backed builder: count equals the range length
        StringBuilder latin = new StringBuilder("abc");
        check(latin.codePointCount(0, 3) == 3, "latin1 full range");
        check(latin.codePointCount(1, 1) == 0, "latin1 empty range");

        // UTF16-backed builder: "a" + U+4E2D + U+1F600 (surrogate pair) + "b"
        // chars: indices 0..4 ; code points: 4
        StringBuilder sb = new StringBuilder("a\u4e2d\uD83D\uDE00b");
        check(sb.length() == 5, "utf16 length");
        check(sb.codePointCount(0, 5) == 4, "full range with surrogate pair");
        check(sb.codePointCount(0, 1) == 1, "leading latin char");
        check(sb.codePointCount(1, 2) == 1, "BMP char");
        check(sb.codePointCount(2, 4) == 1, "surrogate pair counts as one");
        check(sb.codePointCount(1, 3) == 2, "BMP char plus unpaired high surrogate");
        check(sb.codePointCount(3, 5) == 2, "unpaired low surrogate plus latin char");
        check(sb.codePointCount(4, 5) == 1, "trailing latin char");
        check(sb.codePointCount(2, 2) == 0, "empty range in utf16 builder");

        // Lone high surrogate at end of range
        StringBuilder lone = new StringBuilder("\uD83D");
        check(lone.codePointCount(0, 1) == 1, "lone high surrogate");

        // Consistency with String.codePointCount on the same content
        String s = "a\u4e2d\uD83D\uDE00b";
        check(s.codePointCount(0, 5) == sb.codePointCount(0, 5), "consistency with String");
        check(s.codePointCount(1, 3) == sb.codePointCount(1, 3), "consistency for split pair");
    }
}
