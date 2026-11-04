/*
 * @test
 * @summary Tests for java.util.regex.Pattern.compile(String, int)
 * @run main PatternWitupTest
 */

import java.util.regex.Pattern;

public class PatternWitupTest {

    public static void main(String[] args) {
        testCompileWithValidFlags();
        testCompileWithInvalidFlags();
    }

    private static void testCompileWithValidFlags() {
        try {
            Pattern p = Pattern.compile("abc", 0);
            if (p == null) {
                throw new AssertionError("Pattern.compile returned null for valid flags");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid flags: " + e);
        }
    }

    private static void testCompileWithInvalidFlags() {
        int invalidFlags = ~0; // All bits set, likely invalid
        try {
            Pattern p = Pattern.compile("abc", invalidFlags);
            // According to the baseline, this may throw IllegalArgumentException
            // but in current code, it may not. We check if exception is thrown.
            throw new AssertionError("Expected IllegalArgumentException for invalid flags, but none was thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
