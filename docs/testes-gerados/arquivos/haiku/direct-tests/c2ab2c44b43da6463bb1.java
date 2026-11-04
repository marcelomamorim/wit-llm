/*
 * @test
 * @summary Verifies Matcher.end() returns the end index of the last match and throws IllegalStateException when no match is available
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void assertNoMatchThrows(Matcher m, String label) {
        boolean thrown = false;
        try {
            m.end();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, label + ": expected IllegalStateException");
    }

    public static void main(String[] args) {
        Matcher m = Pattern.compile("a+").matcher("baaa");

        assertNoMatchThrows(m, "before find");

        check(m.find(), "find should succeed");
        check(m.end() == 4, "end after find expected 4 but was " + m.end());
        check(m.start() == 1, "start after find expected 1");

        check(!m.find(), "second find should fail");
        assertNoMatchThrows(m, "after failed find");

        Matcher full = Pattern.compile("a+").matcher("aaa");
        check(full.matches(), "matches should succeed");
        check(full.end() == 3, "end after matches expected 3 but was " + full.end());

        Matcher look = Pattern.compile("ab").matcher("abc");
        check(look.lookingAt(), "lookingAt should succeed");
        check(look.end() == 2, "end after lookingAt expected 2 but was " + look.end());

        Matcher reset = Pattern.compile("x").matcher("x");
        check(reset.find(), "find should succeed before reset");
        reset.reset();
        assertNoMatchThrows(reset, "after reset");
    }
}
