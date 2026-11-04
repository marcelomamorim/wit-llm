/*
 * @test
 * @summary Black-box checks of Long.parseLong(String) for valid decimal inputs, boundary values and malformed inputs
 * @run main LongWitupTest
 */
public class LongWitupTest {
    public static void main(String[] args) {
        check(Long.parseLong("0") == 0L);
        check(Long.parseLong("-0") == 0L);
        check(Long.parseLong("123") == 123L);
        check(Long.parseLong("-456") == -456L);
        check(Long.parseLong("+7") == 7L);
        check(Long.parseLong("0042") == 42L);
        check(Long.parseLong("9223372036854775807") == Long.MAX_VALUE);
        check(Long.parseLong("-9223372036854775808") == Long.MIN_VALUE);

        expectNumberFormatException("9223372036854775808");
        expectNumberFormatException("-9223372036854775809");
        expectNumberFormatException("");
        expectNumberFormatException("-");
        expectNumberFormatException("+");
        expectNumberFormatException("abc");
        expectNumberFormatException("1.0");
        expectNumberFormatException(" 1");
        expectNumberFormatException("1 ");
    }

    static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("Check failed");
        }
    }

    static void expectNumberFormatException(String s) {
        try {
            Long.parseLong(s);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("NumberFormatException expected for input: " + s);
    }
}
