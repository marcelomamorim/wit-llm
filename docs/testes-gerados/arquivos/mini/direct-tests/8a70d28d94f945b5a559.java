/*
 * @test
 * @summary Test java.lang.Long.parseUnsignedLong(String) method
 * @run main LongWitupTest
 */

public class LongWitupTest {
    public static void main(String[] args) {
        // Test valid decimal string
        long val1 = Long.parseUnsignedLong("0");
        if (val1 != 0L) throw new RuntimeException("Failed test: parseUnsignedLong(\"0\")");

        long val2 = Long.parseUnsignedLong("123456789");
        if (val2 != 123456789L) throw new RuntimeException("Failed test: parseUnsignedLong(\"123456789\")");

        // Test maximum unsigned long value as decimal string
        // Max unsigned long is 2^64-1, which is "18446744073709551615"
        // parseUnsignedLong returns long, so it cannot represent > Long.MAX_VALUE as signed long
        // But parseUnsignedLong is defined to parse unsigned values, so it should parse correctly
        // However, since return type is long (signed), values > Long.MAX_VALUE wrap around negative
        // So test that parsing "18446744073709551615" returns -1L (two's complement)
        long valMax = Long.parseUnsignedLong("18446744073709551615");
        if (valMax != -1L) throw new RuntimeException("Failed test: parseUnsignedLong(\"18446744073709551615\")");

        // Test invalid input: negative sign
        try {
            Long.parseUnsignedLong("-1");
            throw new RuntimeException("Failed test: parseUnsignedLong(\"-1\") did not throw NumberFormatException");
        } catch (NumberFormatException e) {
            // expected
        }

        // Test invalid input: non-digit characters
        try {
            Long.parseUnsignedLong("123abc");
            throw new RuntimeException("Failed test: parseUnsignedLong(\"123abc\") did not throw NumberFormatException");
        } catch (NumberFormatException e) {
            // expected
        }

        // Test empty string
        try {
            Long.parseUnsignedLong("");
            throw new RuntimeException("Failed test: parseUnsignedLong("") did not throw NumberFormatException");
        } catch (NumberFormatException e) {
            // expected
        }

        // Test null input
        try {
            Long.parseUnsignedLong(null);
            throw new RuntimeException("Failed test: parseUnsignedLong(null) did not throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
