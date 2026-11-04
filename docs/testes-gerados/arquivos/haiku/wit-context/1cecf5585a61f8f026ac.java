/*
 * @test
 * @summary Witup-derived checks for the public Long(String) constructor, covering null and invalid input as NumberFormatException and valid decimal parsing
 * @run main LongWitupTest
 */

public class LongWitupTest {
    public static void main(String[] args) {
        check(0L, "0");
        check(42L, "42");
        check(-42L, "-42");
        check(7L, "+7");
        check(Long.MAX_VALUE, "9223372036854775807");
        check(Long.MIN_VALUE, "-9223372036854775808");

        expectNfe(null);
        expectNfe("");
        expectNfe("-");
        expectNfe("abc");
        expectNfe(" 1");
        expectNfe("1 ");
        expectNfe("9223372036854775808");
        expectNfe("-9223372036854775809");
    }

    @SuppressWarnings("deprecation")
    static void check(long expected, String s) {
        long actual = new Long(s).longValue();
        if (actual != expected) {
            throw new RuntimeException("Expected " + expected + " for input " + s + " but got " + actual);
        }
    }

    @SuppressWarnings("deprecation")
    static void expectNfe(String s) {
        try {
            new Long(s);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("NumberFormatException expected for input: " + s);
    }
}
