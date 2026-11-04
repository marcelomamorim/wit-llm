/*
 * @test
 * @summary Witup-derived tests for Long.parseLong(String)
 * @run main LongWitupTest
 */

public class LongWitupTest {
    public static void main(String[] args) {
        check(Long.parseLong("0") == 0L, "zero");
        check(Long.parseLong("123") == 123L, "positive");
        check(Long.parseLong("-42") == -42L, "negative");
        check(Long.parseLong("+7") == 7L, "plus sign");
        check(Long.parseLong("9223372036854775807") == Long.MAX_VALUE, "max value");
        check(Long.parseLong("-9223372036854775808") == Long.MIN_VALUE, "min value");

        expectNumberFormatException(null, "null input");
        expectNumberFormatException("", "empty input");
        expectNumberFormatException("-", "lone minus sign");
        expectNumberFormatException("abc", "non-digit input");
        expectNumberFormatException("12a", "trailing garbage");
        expectNumberFormatException(" 1", "leading whitespace");
        expectNumberFormatException("9223372036854775808", "overflow");
        expectNumberFormatException("-9223372036854775809", "underflow");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }

    private static void expectNumberFormatException(String input, String message) {
        try {
            Long.parseLong(input);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("Expected NumberFormatException for: " + message);
    }
}
