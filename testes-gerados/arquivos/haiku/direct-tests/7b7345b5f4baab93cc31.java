/*
 * @test
 * @summary Witup-style tests for Pattern.compile(String) observable behavior
 * @run main PatternWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class PatternWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPatternStringIsPreserved() {
        Pattern p = Pattern.compile("a*b");
        check("a*b".equals(p.pattern()), "pattern() should return the compiled regex");
        check("a*b".equals(p.toString()), "toString() should return the compiled regex");
    }

    static void testCompiledPatternMatchesExpectedInput() {
        Pattern p = Pattern.compile("a*b");
        Matcher m1 = p.matcher("aaaaab");
        check(m1.matches(), "'aaaaab' should match a*b");
        Matcher m2 = p.matcher("aaa");
        check(!m2.matches(), "'aaa' should not match a*b");
    }

    static void testEmptyRegex() {
        Pattern p = Pattern.compile("");
        check("".equals(p.pattern()), "empty regex should be preserved");
        check(p.matcher("").matches(), "empty regex should match empty input");
        check(!p.matcher("x").matches(), "empty regex should not match 'x'");
    }

    static void testEachCallReturnsNewInstance() {
        Pattern p1 = Pattern.compile("abc");
        Pattern p2 = Pattern.compile("abc");
        check(p1 != p2, "compile should return a new Pattern instance on each call");
        check(p1.pattern().equals(p2.pattern()), "equal regexes should have equal pattern strings");
    }

    static void testInvalidRegexThrowsPatternSyntaxException() {
        boolean thrown = false;
        try {
            Pattern.compile("(");
        } catch (PatternSyntaxException e) {
            thrown = true;
        }
        check(thrown, "unclosed group '(' should throw PatternSyntaxException");
    }

    public static void main(String[] args) {
        testPatternStringIsPreserved();
        testCompiledPatternMatchesExpectedInput();
        testEmptyRegex();
        testEachCallReturnsNewInstance();
        testInvalidRegexThrowsPatternSyntaxException();
    }
}
