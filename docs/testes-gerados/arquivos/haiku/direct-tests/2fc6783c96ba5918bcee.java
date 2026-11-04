/*
 * @test
 * @summary Verifies observable behavior of String.getBytes() using the default charset
 * @run main StringWitupTest
 */

import java.nio.charset.Charset;
import java.util.Arrays;

public class StringWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAsciiBytes() {
        byte[] bytes = "abc".getBytes();
        check(bytes.length == 3, "expected length 3 for \"abc\", got " + bytes.length);
        check(bytes[0] == 'a' && bytes[1] == 'b' && bytes[2] == 'c',
              "unexpected bytes for \"abc\": " + Arrays.toString(bytes));
    }

    static void testEmptyString() {
        byte[] bytes = "".getBytes();
        check(bytes != null, "getBytes() returned null for empty string");
        check(bytes.length == 0, "expected empty array for empty string, got length " + bytes.length);
    }

    static void testMatchesExplicitDefaultCharset() {
        String s = "h\u00e9llo \u4e16\u754c";
        byte[] expected = s.getBytes(Charset.defaultCharset());
        byte[] actual = s.getBytes();
        check(Arrays.equals(expected, actual),
              "getBytes() differs from getBytes(Charset.defaultCharset())");
    }

    static void testRoundTripWithDefaultCharset() {
        String s = "h\u00e9llo \u4e16\u754c";
        String back = new String(s.getBytes(), Charset.defaultCharset());
        check(s.equals(back), "round trip through default charset failed");
    }

    static void testReturnsFreshArray() {
        String s = "abc";
        byte[] first = s.getBytes();
        first[0] = 'z';
        byte[] second = s.getBytes();
        check(second[0] == 'a', "modifying returned array affected subsequent getBytes() result");
        check(s.equals("abc"), "string content changed after modifying returned array");
    }

    public static void main(String[] args) {
        testAsciiBytes();
        testEmptyString();
        testMatchesExplicitDefaultCharset();
        testRoundTripWithDefaultCharset();
        testReturnsFreshArray();
    }
}
