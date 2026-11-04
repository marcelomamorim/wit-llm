/*
 * @test
 * @summary Behavioral tests for Pattern.matches(String, CharSequence)
 * @run main PatternWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class PatternWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        check(Pattern.matches("a*b", "aaaaab"), "a*b should match aaaaab");
        check(!Pattern.matches("a*b", "aaaac"), "a*b should not match aaaac");
        check(!Pattern.matches("a", "ab"), "matches must require the whole input");
        check(!Pattern.matches("ab", "a"), "matches must not accept a partial input");
        check(Pattern.matches("", ""), "empty regex should match empty input");
        check(!Pattern.matches("", "x"), "empty regex should not match non-empty input");
        check(Pattern.matches("[0-9]+", new StringBuilder("12345")),
              "CharSequence input should be matched by content");
        check(!Pattern.matches("[0-9]+", new StringBuilder("12a45")),
              "non-digit CharSequence input should not match");

        boolean thrown = false;
        try {
            Pattern.matches("(", "x");
        } catch (PatternSyntaxException e) {
            thrown = true;
        }
        check(thrown, "invalid regex should throw PatternSyntaxException");
    }
}
