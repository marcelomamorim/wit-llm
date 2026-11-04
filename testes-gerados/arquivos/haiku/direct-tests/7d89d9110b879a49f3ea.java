/*
 * @test
 * @summary Verifies trailing whitespace stripping on UTF16-coded strings, which reaches StringUTF16.stripTrailing through String.stripTrailing
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {
    public static void main(String[] args) {
        check("\u4e2d\u6587  ", "\u4e2d\u6587");
        check("\u4e2d\t\n ", "\u4e2d");
        check("  \u4e2d", "  \u4e2d");
        check("\u4e2d\u4e2d", "\u4e2d\u4e2d");
        check("\u4e2d\u3000 x ", "\u4e2d\u3000 x");
        check("\u4e2d  \u0020", "\u4e2d");
    }

    static void check(String input, String expected) {
        String actual = input.stripTrailing();
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected [" + expected + "] but got [" + actual + "]");
        }
    }
}
