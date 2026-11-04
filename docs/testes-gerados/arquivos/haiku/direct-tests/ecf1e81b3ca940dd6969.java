/*
 * @test
 * @summary Basic observable behavior of Pattern.compile(String, int)
 * @run main PatternWitupTest
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternWitupTest {
    public static void main(String[] args) {
        Pattern p = Pattern.compile("abc", 0);
        check(p.pattern().equals("abc"), "pattern source mismatch");
        check(p.matcher("abc").matches(), "plain pattern should match");
        check(!p.matcher("ABC").matches(), "pattern should be case-sensitive with flags 0");

        Pattern ci = Pattern.compile("abc", Pattern.CASE_INSENSITIVE);
        check(ci.matcher("ABC").matches(), "CASE_INSENSITIVE should match uppercase input");
        check((ci.flags() & Pattern.CASE_INSENSITIVE) != 0, "CASE_INSENSITIVE flag not reported by flags()");

        Pattern lit = Pattern.compile("a.b", Pattern.LITERAL);
        check(lit.matcher("a.b").matches(), "LITERAL pattern should match its literal text");
        check(!lit.matcher("axb").matches(), "LITERAL dot must not act as wildcard");

        Pattern ml = Pattern.compile("^x$", Pattern.MULTILINE);
        Matcher m = ml.matcher("a\nx\nb");
        check(m.find(), "MULTILINE anchors should find middle line");

        Pattern dot = Pattern.compile("a.b", Pattern.DOTALL);
        check(dot.matcher("a\nb").matches(), "DOTALL should let '.' match newline");

        Pattern noDot = Pattern.compile("a.b", 0);
        check(!noDot.matcher("a\nb").matches(), "without DOTALL '.' must not match newline");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
