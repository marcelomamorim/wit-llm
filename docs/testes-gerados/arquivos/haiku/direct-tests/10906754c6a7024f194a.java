/*
 * @test
 * @summary Verifies observable results and bounds exceptions of String.substring(int,int)
 * @run main StringWitupTest
 */

public class StringWitupTest {

    public static void main(String[] args) {
        String s = "abcdef";

        check("bcd".equals(s.substring(1, 4)), "middle substring");
        check("".equals(s.substring(2, 2)), "empty substring when begin == end");
        check("abc".equals(s.substring(0, 3)), "prefix substring");
        check("def".equals(s.substring(3, 6)), "suffix substring up to length");
        check(s.substring(0, s.length()) == s, "full range returns same instance");
        check("".equals("".substring(0, 0)), "empty string full range");

        String u = "a\u4E2Db\u20AC";
        check("\u4E2D".equals(u.substring(1, 2)), "non-Latin1 single char");
        check("\u4E2Db".equals(u.substring(1, 3)), "non-Latin1 multi char");
        check("\u20AC".equals(u.substring(3, 4)), "non-Latin1 trailing char");

        expectSIOOBE(s, -1, 2);
        expectSIOOBE(s, 0, 7);
        expectSIOOBE(s, 3, 2);
        expectSIOOBE("", 0, 1);
    }

    static void expectSIOOBE(String target, int begin, int end) {
        try {
            target.substring(begin, end);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected StringIndexOutOfBoundsException for begin="
                + begin + " end=" + end);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
