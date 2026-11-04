/*
 * @test
 * @summary Witup-derived checks for Long.parseUnsignedLong(String)
 * @run main LongWitupTest
 */

public class LongWitupTest {
    public static void main(String[] args) {
        check("0", 0L);
        check("9223372036854775807", Long.MAX_VALUE);
        check("9223372036854775808", Long.MIN_VALUE);
        check("18446744073709551615", -1L);
        checkNfe(null);
        checkNfe("");
        checkNfe("-1");
        checkNfe("abc");
        checkNfe("18446744073709551616");
    }

    static void check(String s, long expected) {
        long actual = Long.parseUnsignedLong(s);
        if (actual != expected) {
            throw new RuntimeException("Expected " + expected + " but got " + actual + " for: " + s);
        }
    }

    static void checkNfe(String s) {
        try {
            Long.parseUnsignedLong(s);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("NumberFormatException expected for: " + s);
    }
}
