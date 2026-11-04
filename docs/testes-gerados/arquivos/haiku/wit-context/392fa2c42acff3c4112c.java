/*
 * @test
 * @summary WITUP-derived checks for StringTokenizer.nextToken(String delim)
 * @run main StringTokenizerWitupTest
 */

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class StringTokenizerWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        StringTokenizer st = new StringTokenizer("a,b;c", ",");
        check("a,b".equals(st.nextToken(";")), "first token with ';' delimiter");
        check("c".equals(st.nextToken(";")), "second token with ';' delimiter");
        check(!st.hasMoreTokens(), "no tokens should remain");
        boolean thrown = false;
        try {
            st.nextToken(";");
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "nextToken(delim) at end must throw NoSuchElementException");

        StringTokenizer st2 = new StringTokenizer("abc", ",");
        check("abc".equals(st2.nextToken("")), "empty delimiter string must yield whole input");
        thrown = false;
        try {
            st2.nextToken("");
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "exhausted tokenizer must throw NoSuchElementException");
    }
}
