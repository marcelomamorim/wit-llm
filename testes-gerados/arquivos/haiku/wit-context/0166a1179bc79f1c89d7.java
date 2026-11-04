/*
 * @test
 * @summary Verify Matcher.start(String) for named groups, null names, unknown names, no match and unmatched groups
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    interface Action {
        void run();
    }

    static void expectThrows(Class<? extends Throwable> type, Action action) {
        try {
            action.run();
        } catch (Throwable t) {
            if (type.isInstance(t)) {
                return;
            }
            throw new RuntimeException("Unexpected exception: " + t, t);
        }
        throw new RuntimeException("Expected " + type.getName());
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Pattern p = Pattern.compile("(?<year>\\d{4})-(?<month>\\d{2})");
        Matcher m = p.matcher("x2018-09");

        expectThrows(IllegalStateException.class, () -> m.start("year"));

        check(m.find(), "find() should succeed");
        check(m.start("year") == 1, "start(year) expected 1 but was " + m.start("year"));
        check(m.start("month") == 6, "start(month) expected 6 but was " + m.start("month"));

        expectThrows(IllegalArgumentException.class, () -> m.start("day"));
        expectThrows(NullPointerException.class, () -> m.start(null));

        Pattern q = Pattern.compile("(?<a>x)|(?<b>y)");
        Matcher n = q.matcher("y");
        check(n.find(), "find() should succeed for alternation");
        check(n.start("a") == -1, "unmatched group start expected -1 but was " + n.start("a"));
        check(n.start("b") == 0, "matched group start expected 0 but was " + n.start("b"));
    }
}
