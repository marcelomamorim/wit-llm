/*
 * @test
 * @summary Test java.util.regex.Matcher.group() method behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testGroupAfterMatch();
        testGroupWithoutMatch();
    }

    private static void testGroupAfterMatch() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        if (!m.matches()) {
            throw new RuntimeException("Expected match did not occur");
        }
        String g0 = m.group();
        String g1 = m.group(0);
        String g2 = m.group(1);
        String g3 = m.group(2);

        if (!"ab".equals(g0)) {
            throw new RuntimeException("group() returned '" + g0 + "' instead of 'ab'");
        }
        if (!"ab".equals(g1)) {
            throw new RuntimeException("group(0) returned '" + g1 + "' instead of 'ab'");
        }
        if (!"a".equals(g2)) {
            throw new RuntimeException("group(1) returned '" + g2 + "' instead of 'a'");
        }
        if (!"b".equals(g3)) {
            throw new RuntimeException("group(2) returned '" + g3 + "' instead of 'b'");
        }
    }

    private static void testGroupWithoutMatch() {
        Pattern p = Pattern.compile("abc");
        Matcher m = p.matcher("def");
        boolean thrown = false;
        try {
            m.group();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException when calling group() without a successful match");
        }
    }
}
