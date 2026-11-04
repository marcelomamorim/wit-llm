/*
 * @test
 * @summary Test the static compile method of java.util.regex.Pattern
 * @run main PatternWitupTest
 */

import java.util.regex.Pattern;

public class PatternWitupTest {

    public static void main(String[] args) {
        testCompileWithSimpleRegex();
        testCompileWithEmptyRegex();
        testCompileWithNullRegex();
    }

    static void testCompileWithSimpleRegex() {
        Pattern p = Pattern.compile("abc");
        if (p == null) {
            throw new AssertionError("Pattern.compile returned null for " + "abc");
        }
    }

    static void testCompileWithEmptyRegex() {
        Pattern p = Pattern.compile("");
        if (p == null) {
            throw new AssertionError("Pattern.compile returned null for empty string");
        }
    }

    static void testCompileWithNullRegex() {
        try {
            Pattern p = Pattern.compile(null);
            throw new AssertionError("Expected NullPointerException for null regex");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
