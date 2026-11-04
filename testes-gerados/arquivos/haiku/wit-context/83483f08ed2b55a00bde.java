/*
 * @test
 * @summary Verifies Matcher.find(int) bounds checking and observable search behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        Matcher m = Pattern.compile("b").matcher("abc");

        expectIndexOutOfBounds(m, -1);
        expectIndexOutOfBounds(m, 4);

        if (m.find(3)) {
            throw new AssertionError("find(3) on 'abc' with 'b' should be false");
        }

        if (!m.find(0)) {
            throw new AssertionError("find(0) should match 'b'");
        }
        if (m.start() != 1) {
            throw new AssertionError("expected start 1 but was " + m.start());
        }

        if (m.find(2)) {
            throw new AssertionError("find(2) on 'abc' with 'b' should be false");
        }

        if (!m.find(1) || m.start() != 1) {
            throw new AssertionError("find(1) should match 'b' at index 1");
        }

        Matcher empty = Pattern.compile("").matcher("abc");
        if (!empty.find(3) || empty.start() != 3 || empty.end() != 3) {
            throw new AssertionError("empty pattern should match at end index 3");
        }
    }

    private static void expectIndexOutOfBounds(Matcher m, int start) {
        try {
            m.find(start);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new AssertionError("expected IndexOutOfBoundsException for start " + start);
    }
}
