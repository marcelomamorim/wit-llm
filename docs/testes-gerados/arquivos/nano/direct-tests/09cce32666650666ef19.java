/* @test    @summary Test Long.valueOf(String s, int radix) for various inputs    @run main LongWitupTest */

import java.lang.Long;

public class LongWitupTest {

    public static void main(String[] args) {
        testValidRadix10();
        testValidRadix16();
        testInvalidRadix();
        testNegativeNumber();
        testZero();
        testLargeNumber();
        testInvalidNumber();
    }

    static void testValidRadix10() {
        Long result = Long.valueOf("12345", 10);
        assert result != null : "Result should not be null";
        assert result.equals(Long.valueOf(12345L)) : "Expected 12345, got " + result;
    }

    static void testValidRadix16() {
        Long result = Long.valueOf("1a", 16);
        assert result != null : "Result should not be null";
        assert result.equals(Long.valueOf(26L)) : "Expected 26, got " + result;
    }

    static void testInvalidRadix() {
        try {
            Long.valueOf("123", 1);
            assert false : "Expected NumberFormatException for radix 1";
        } catch (NumberFormatException e) {
            // expected
        }
        try {
            Long.valueOf("123", 37);
            assert false : "Expected NumberFormatException for radix 37";
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testNegativeNumber() {
        Long result = Long.valueOf("-987", 10);
        assert result != null : "Result should not be null";
        assert result.equals(Long.valueOf(-987L)) : "Expected -987, got " + result;
    }

    static void testZero() {
        Long result = Long.valueOf("0", 10);
        assert result != null : "Result should not be null";
        assert result.equals(Long.valueOf(0L)) : "Expected 0, got " + result;
    }

    static void testLargeNumber() {
        String largeNumberStr = "9223372036854775807"; // Long.MAX_VALUE
        Long result = Long.valueOf(largeNumberStr, 10);
        assert result != null : "Result should not be null";
        assert result.equals(Long.MAX_VALUE) : "Expected Long.MAX_VALUE, got " + result;
    }

    static void testInvalidNumber() {
        try {
            Long.valueOf("notANumber", 10);
            assert false : "Expected NumberFormatException for invalid number";
        } catch (NumberFormatException e) {
            // expected
        }
    }
}
