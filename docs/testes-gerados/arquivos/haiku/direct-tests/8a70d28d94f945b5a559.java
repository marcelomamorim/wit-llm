/*
 * @test
 * @summary Tests Long.parseUnsignedLong(String) observable results and NumberFormatException cases
 * @run main LongWitupTest
 */

public class LongWitupTest {

    public static void main(String[] args) {
        check(Long.parseUnsignedLong("0") == 0L, "zero");
        check(Long.parseUnsignedLong("42") == 42L, "simple positive");
        check(Long.parseUnsignedLong("9223372036854775807") == Long.MAX_VALUE, "max signed value");
        check(Long.parseUnsignedLong("9223372036854775808") == Long.MIN_VALUE, "2^63 wraps to MIN_VALUE");
        check(Long.parseUnsignedLong("18446744073709551615") == -1L, "2^64-1 is all bits set");
        check(Long.parseUnsignedLong("12345678901234567890")
              == Long.parseUnsignedLong("12345678901234567890", 10), "consistent with radix 10 overload");

        expectNFE("");
        expectNFE("-1");
        expectNFE("abc");
        expectNFE("18446744073709551616");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }

    static void expectNFE(String s) {
        try {
            Long.parseUnsignedLong(s);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("Expected NumberFormatException for: \"" + s + "\"");
    }
}
