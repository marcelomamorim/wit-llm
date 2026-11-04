/*
 * @test
 * @summary Test Pattern.compile(String regex, int flags) basic behavior
 * @run main PatternWitupTest
 */

import java.util.regex.Pattern;

public class PatternWitupTest {
    public static void main(String[] args) {
        testCompileSimplePattern();
        testCompileWithFlags();
        testCompileEmptyPattern();
        testCompileNullRegex();
    }

    private static void testCompileSimplePattern() {
        Pattern p = Pattern.compile("abc", 0);
        if (!p.pattern().equals("abc")) {
            throw new AssertionError("Pattern string mismatch");
        }
        if (p.flags() != 0) {
            throw new AssertionError("Pattern flags mismatch");
        }
    }

    private static void testCompileWithFlags() {
        int flags = Pattern.CASE_INSENSITIVE | Pattern.MULTILINE;
        Pattern p = Pattern.compile("def", flags);
        if (!p.pattern().equals("def")) {
            throw new AssertionError("Pattern string mismatch with flags");
        }
        if (p.flags() != flags) {
            throw new AssertionError("Pattern flags mismatch with flags");
        }
    }

    private static void testCompileEmptyPattern() {
        Pattern p = Pattern.compile("", 0);
        if (!p.pattern().equals("")) {
            throw new AssertionError("Empty pattern string mismatch");
        }
        if (p.flags() != 0) {
            throw new AssertionError("Empty pattern flags mismatch");
        }
    }

    private static void testCompileNullRegex() {
        try {
            Pattern.compile(null, 0);
            throw new AssertionError("Expected NullPointerException for null regex");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
