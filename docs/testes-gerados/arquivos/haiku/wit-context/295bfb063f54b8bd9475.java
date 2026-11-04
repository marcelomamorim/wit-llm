/*
 * @test
 * @summary Verify StringTokenizer.hasMoreElements observable behavior, including NullPointerException for null delimiters
 * @run main StringTokenizerWitupTest
 */
public class StringTokenizerWitupTest {
    public static void main(String[] args) {
        StringTokenizer st = new StringTokenizer("a b", " ");
        check(st.hasMoreElements(), "expected true for non-empty input");
        st.nextToken();
        check(st.hasMoreElements(), "expected true before second token");
        st.nextToken();
        check(!st.hasMoreElements(), "expected false after exhausting tokens");

        check(!new StringTokenizer("", " ").hasMoreElements(), "expected false for empty string");
        check(!new StringTokenizer("   ", " ").hasMoreElements(), "expected false for only delimiters");
        check(new StringTokenizer("   ", " ", true).hasMoreElements(), "expected true for delimiters returned as tokens");

        boolean npe = false;
        try {
            new StringTokenizer("abc", null).hasMoreElements();
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "expected NullPointerException when delimiters is null");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
