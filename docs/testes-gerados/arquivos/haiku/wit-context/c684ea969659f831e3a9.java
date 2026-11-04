/*
 * @test
 * @summary Observable behavior of Pattern.matches(String, CharSequence)
 * @run main PatternWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class PatternWitupTest {
    public static void main(String[] args) {
        check(Pattern.matches("a*b", "aaaaab"));
        check(!Pattern.matches("a*b", "aaaac"));
        check(!Pattern.matches("a", "ab"));
        check(Pattern.matches("", ""));
        check(Pattern.matches("ab|cd", new StringBuilder("cd")));
        boolean thrown = false;
        try {
            Pattern.matches("(", "x");
        } catch (PatternSyntaxException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected PatternSyntaxException for invalid regex");
        }
    }

    private static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("unexpected result");
        }
    }
}
