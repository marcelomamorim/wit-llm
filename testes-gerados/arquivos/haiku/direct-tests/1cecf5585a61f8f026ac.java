/*
 * @test
 * @summary Witup-style tests for the public Long(String) constructor, which delegates to parseLong(s, 10)
 * @run main LongWitupTest
 */

public class LongWitupTest {

    public static void main(String[] args) {
        testValidDecimalValues();
        testBoundaryValues();
        testInvalidInputsThrowNumberFormatException();
    }

    static void testValidDecimalValues() {
        check(new Long("0").longValue() == 0L, "zero");
        check(new Long("123").longValue() == 123L, "positive");
        check(new Long("-45").longValue() == -45L, "negative");
        check(new Long("+7").longValue() == 7L, "explicit plus sign");
        check(new Long("-0").longValue() == 0L, "negative zero");
    }

    static void testBoundaryValues() {
        check(new Long("9223372036854775807").longValue() == Long.MAX_VALUE, "MAX_VALUE");
        check(new Long("-9223372036854775808").longValue() == Long.MIN_VALUE, "MIN_VALUE");
    }

    static void testInvalidInputsThrowNumberFormatException() {
        expectNumberFormatException(null);
        expectNumberFormatException("");
        expectNumberFormatException("abc");
        expectNumberFormatException(" 1");
        expectNumberFormatException("1 ");
        expectNumberFormatException("1.0");
        expectNumberFormatException("9223372036854775808");
        expectNumberFormatException("-9223372036854775809");
    }

    static void expectNumberFormatException(String s) {
        try {
            new Long(s);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("Expected NumberFormatException for input: " + s);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
