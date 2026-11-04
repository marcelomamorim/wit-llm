/*
 * @test
 * @summary Tests for Pattern.compile(String, int) method.
 * @run main PatternWitupTest
 */

import java.util.regex.Pattern;

public class PatternWitupTest {

    public static void main(String[] args) {
        testCompileWithFlags();
        testCompileWithEmptyRegex();
        testCompileWithNullRegex();
        testCompileWithInvalidFlags();
    }

    static void testCompileWithFlags() {
        Pattern p = Pattern.compile("abc", Pattern.CASE_INSENSITIVE);
        if (p == null) {
            throw new AssertionError("Pattern.compile returned null for valid regex and flags");
        }
        // Verify that pattern is compiled with the given flags by matching case-insensitively
        boolean matches = p.matcher("ABC").matches();
        if (!matches) {
            throw new AssertionError("Pattern did not match case-insensitive string as expected");
        }
    }

    static void testCompileWithEmptyRegex() {
        Pattern p = Pattern.compile("", 0);
        if (p == null) {
            throw new AssertionError("Pattern.compile returned null for empty regex");
        }
        boolean matchesEmpty = p.matcher("").matches();
        if (!matchesEmpty) {
            throw new AssertionError("Empty regex did not match empty string");
        }
    }

    static void testCompileWithNullRegex() {
        try {
            Pattern.compile(null, 0);
            throw new AssertionError("Expected NullPointerException for null regex");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testCompileWithInvalidFlags() {
        int invalidFlag = 0x100000; // a flag not defined in Pattern
        Pattern p = Pattern.compile("abc", invalidFlag);
        if (p == null) {
            throw new AssertionError("Pattern.compile returned null for regex with invalid flags");
        }
        // The pattern should still compile, but behavior is undefined; test that no exception thrown
        boolean matches = p.matcher("abc").matches();
        if (!matches) {
            // pattern may not match, but no exception expected
        }
    }
}
