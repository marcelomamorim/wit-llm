/*
 * @test
 * @summary Verifies charAt behavior for UTF16-backed strings through the public String API, which delegates to StringUTF16.charAt
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        String cjk = "\u4e2d\u6587\u5b57";
        check(cjk.charAt(0) == '\u4e2d', "cjk index 0");
        check(cjk.charAt(1) == '\u6587', "cjk index 1");
        check(cjk.charAt(2) == '\u5b57', "cjk index 2");

        String mixed = "a\u4e2db";
        check(mixed.charAt(0) == 'a', "mixed index 0");
        check(mixed.charAt(1) == '\u4e2d', "mixed index 1");
        check(mixed.charAt(2) == 'b', "mixed index 2");

        String sur = "x\ud840\udc00y";
        check(sur.charAt(0) == 'x', "surrogate prefix");
        check(sur.charAt(1) == '\ud840', "high surrogate returned as code unit");
        check(sur.charAt(2) == '\udc00', "low surrogate returned as code unit");
        check(sur.charAt(3) == 'y', "surrogate suffix");

        String latin = "abc";
        check(latin.charAt(1) == 'b', "latin1 control");

        String s = "\u4e2d\u6587\u5b57";
        for (int i = 0; i < s.length(); i++) {
            check(s.charAt(i) == s.toCharArray()[i], "consistency at " + i);
        }

        try {
            s.charAt(3);
            throw new RuntimeException("expected exception for index == length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

        try {
            s.charAt(-1);
            throw new RuntimeException("expected exception for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
