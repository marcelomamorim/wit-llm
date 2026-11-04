/*
 * @test
 * @summary Test basic functionality of Pattern.compile(String) including null and empty input
 * @run main PatternWitupTest
 */

import java.util.regex.Pattern;

public class PatternWitupTest {
    public static void main(String[] args) {
        testCompileNull();
        testCompileEmpty();
        testCompileSimplePattern();
    }

    private static void testCompileNull() {
        try {
            Pattern.compile(null);
            throw new Error("Expected NullPointerException for null regex");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testCompileEmpty() {
        Pattern p = Pattern.compile("");
        if (p == null) {
            throw new Error("Pattern.compile("") returned null");
        }
        // Empty pattern should match empty string
        if (!p.matcher("").matches()) {
            throw new Error("Empty pattern should match empty string");
        }
    }

    private static void testCompileSimplePattern() {
        Pattern p = Pattern.compile("a*b");
        if (p == null) {
            throw new Error("Pattern.compile("a*b") returned null");
        }
        // Test matching "aaaaab"
        if (!p.matcher("aaaaab").matches()) {
            throw new Error("Pattern 'a*b' should match 'aaaaab'");
        }
        // Test not matching "bba"
        if (p.matcher("bba").matches()) {
            throw new Error("Pattern 'a*b' should not match 'bba'");
        }
    }
}
