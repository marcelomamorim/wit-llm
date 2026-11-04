/*
 * @test
 * @summary Verifies Matcher.end(int) exception behavior when no match is available and for invalid group indexes, and checks end offsets for valid groups.
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

    static void expectIllegalState(Matcher m, int group) {
        try {
            m.end(group);
        } catch (IllegalStateException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalStateException for group " + group);
    }

    static void expectIndexOutOfBounds(Matcher m, int group) {
        try {
            m.end(group);
        } catch (IndexOutOfBoundsException expected) {
            return;
        }
        throw new RuntimeException("Expected IndexOutOfBoundsException for group " + group);
    }

    public static void main(String[] args) {
        Matcher noMatch = Pattern.compile("z").matcher("abc");
        check(!noMatch.find(), "find should return false when no match exists");
        expectIllegalState(noMatch, 0);

        Matcher fresh = Pattern.compile("(a)(b)").matcher("xaby");
        expectIllegalState(fresh, 0);

        Matcher m = Pattern.compile("(a)(b)").matcher("xaby");
        check(m.find(), "find should locate 'ab' in 'xaby'");
        check(m.end(0) == 3, "end(0) expected 3 but was " + m.end(0));
        check(m.end(1) == 2, "end(1) expected 2 but was " + m.end(1));
        check(m.end(2) == 3, "end(2) expected 3 but was " + m.end(2));

        expectIndexOutOfBounds(m, -1);
        expectIndexOutOfBounds(m, 3);
        expectIndexOutOfBounds(m, Integer.MAX_VALUE);

        Matcher after = Pattern.compile("a").matcher("ba");
        check(after.find(), "find should locate 'a' in 'ba'");
        check(after.end(0) == 2, "end(0) expected 2 but was " + after.end(0));
        check(!after.find(), "second find should return false");
        expectIllegalState(after, 0);
    }
}
