/*
 * @test
 * @summary Verifies observable behavior of StringTokenizer.hasMoreTokens()
 * @run main StringTokenizerWitupTest
 */

public class StringTokenizerWitupTest {

    public static void main(String[] args) {
        check(!new StringTokenizer("").hasMoreTokens(), "empty string has no tokens");

        StringTokenizer st = new StringTokenizer("a b");
        check(st.hasMoreTokens(), "first call should report a token");
        check(st.hasMoreTokens(), "repeated hasMoreTokens must not advance");
        check("a".equals(st.nextToken()), "first token should be a");
        check(st.hasMoreTokens(), "second token should be reported");
        check("b".equals(st.nextToken()), "second token should be b");
        check(!st.hasMoreTokens(), "no tokens should remain");

        check(!new StringTokenizer("   ").hasMoreTokens(), "only default delimiters means no tokens");
        check(!new StringTokenizer(",,,", ",").hasMoreTokens(), "only custom delimiters means no tokens");

        StringTokenizer rd = new StringTokenizer(" ", " ", true);
        check(rd.hasMoreTokens(), "delimiter should be reported when returnDelims is true");
        check(" ".equals(rd.nextToken()), "delimiter should be returned as token");
        check(!rd.hasMoreTokens(), "no tokens should remain after delimiter");

        StringTokenizer tail = new StringTokenizer("x,,", ",");
        check(tail.hasMoreTokens(), "x should be reported");
        check("x".equals(tail.nextToken()), "token should be x");
        check(!tail.hasMoreTokens(), "trailing delimiters should yield no tokens");

        StringTokenizer st2 = new StringTokenizer("a,b c");
        check("a".equals(st2.nextToken(",")), "nextToken with new delimiters should return a");
        check(st2.hasMoreTokens(), "hasMoreTokens should see b c after delimiter change");
        check("b c".equals(st2.nextToken()), "space should not be a delimiter after change");
        check(!st2.hasMoreTokens(), "no tokens should remain");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
