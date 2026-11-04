/*
 * @test
 * @summary Test java.util.regex.Pattern.compile(String regex, int flags) for valid and invalid flags
 * @run main PatternWitupTest
 */

import java.util.regex.Pattern;

public class PatternWitupTest {
    public static void main(String[] args) {
        testValidFlags();
        testInvalidFlags();
        System.out.println("All tests passed.");
    }

    private static void testValidFlags() {
        // Test with no flags
        Pattern p1 = Pattern.compile("abc", 0);
        if (p1 == null) throw new AssertionError("Pattern.compile returned null for flags=0");

        // Test with each valid flag individually
        int[] validFlags = {
            Pattern.CASE_INSENSITIVE,
            Pattern.MULTILINE,
            Pattern.DOTALL,
            Pattern.UNICODE_CASE,
            Pattern.CANON_EQ,
            Pattern.UNIX_LINES,
            Pattern.LITERAL,
            Pattern.UNICODE_CHARACTER_CLASS,
            Pattern.COMMENTS
        };

        for (int flag : validFlags) {
            Pattern p = Pattern.compile("abc", flag);
            if (p == null) throw new AssertionError("Pattern.compile returned null for flag=" + flag);
        }

        // Test with combination of valid flags
        int combinedFlags = Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL;
        Pattern pCombined = Pattern.compile("abc", combinedFlags);
        if (pCombined == null) throw new AssertionError("Pattern.compile returned null for combined flags");
    }

    private static void testInvalidFlags() {
        // Flags with bits outside the allowed mask should cause IllegalArgumentException
        // Allowed flags mask:
        int allowedFlags = Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL |
                           Pattern.UNICODE_CASE | Pattern.CANON_EQ | Pattern.UNIX_LINES |
                           Pattern.LITERAL | Pattern.UNICODE_CHARACTER_CLASS | Pattern.COMMENTS;

        int invalidFlag = ~allowedFlags; // bits outside allowed flags

        // Try some invalid flags by setting bits outside allowedFlags
        int[] invalidFlagsArray = {
            invalidFlag & 0xFF,
            invalidFlag & 0xFFFF,
            invalidFlag,
            1 << 31 // highest bit set
        };

        for (int invalid : invalidFlagsArray) {
            try {
                Pattern.compile("abc", invalid);
                throw new AssertionError("Expected IllegalArgumentException for invalid flags: " + invalid);
            } catch (IllegalArgumentException e) {
                // Expected
            }
        }
    }
}
