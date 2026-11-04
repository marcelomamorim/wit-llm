/*
 * @test
 * @summary Witup-derived tests for URLDecoder.decode(String, Charset)
 * @run main URLDecoderWitupTest
 */

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class URLDecoderWitupTest {

    public static void main(String[] args) {
        testNullCharsetThrowsNpe();
        check("a b", URLDecoder.decode("a+b", StandardCharsets.UTF_8));
        check("AB", URLDecoder.decode("%41%42", StandardCharsets.UTF_8));
        check("\u00e9", URLDecoder.decode("%C3%A9", StandardCharsets.UTF_8));
        check("abc", URLDecoder.decode("abc", StandardCharsets.UTF_8));
        testIllegalArgument("%zz");
        testIllegalArgument("%4");
    }

    static void testNullCharsetThrowsNpe() {
        try {
            URLDecoder.decode("abc", null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null charset");
    }

    static void testIllegalArgument(String s) {
        try {
            URLDecoder.decode(s, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for input: " + s);
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected [" + expected + "] but was [" + actual + "]");
        }
    }
}
