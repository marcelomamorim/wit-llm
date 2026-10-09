/* @test    @summary Tests for java.net.URLDecoder.decode(String s, Charset charset)    @run main URLDecoderWitupTest_Pd316b42b2953 */

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class URLDecoderWitupTest_Pd316b42b2953 {

    public static void main(String[] args) {
        testDecodeWithNullCharset();
        testDecodeWithValidEncoding();
        testDecodeWithPercentEncoding();
        testDecodeWithPlusSign();
        testDecodeWithIncompletePercentEncoding();
        testDecodeWithInvalidHex();
    }

    static void testDecodeWithNullCharset() {
        String input = "hello%20world";
        try {
            URLDecoder.decode(input, null);
            System.out.println("FAIL: Expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        }
    }

    static void testDecodeWithValidEncoding() {
        String input = "hello+world";
        String result = URLDecoder.decode(input, StandardCharsets.UTF_8);
        if ("hello world".equals(result)) {
            System.out.println("PASS: Decoded plus sign to space");
        } else {
            System.out.println("FAIL: Unexpected decoding result: " + result);
        }
    }

    static void testDecodeWithPercentEncoding() {
        String input = "hello%20world";
        String result = URLDecoder.decode(input, StandardCharsets.UTF_8);
        if ("hello world".equals(result)) {
            System.out.println("PASS: Decoded percent encoding correctly");
        } else {
            System.out.println("FAIL: Unexpected decoding result: " + result);
        }
    }

    static void testDecodeWithPlusSign() {
        String input = "a+b+c";
        String result = URLDecoder.decode(input, StandardCharsets.UTF_8);
        if ("a b c".equals(result)) {
            System.out.println("PASS: Plus sign converted to space");
        } else {
            System.out.println("FAIL: Unexpected decoding result: " + result);
        }
    }

    static void testDecodeWithIncompletePercentEncoding() {
        String input = "hello%2";
        try {
            URLDecoder.decode(input, StandardCharsets.UTF_8);
            System.out.println("FAIL: Expected IllegalArgumentException for incomplete percent encoding");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException thrown as expected");
        }
    }

    static void testDecodeWithInvalidHex() {
        String input = "hello%ZZ";
        try {
            URLDecoder.decode(input, StandardCharsets.UTF_8);
            System.out.println("FAIL: Expected IllegalArgumentException for invalid hex");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException thrown as expected");
        }
    }
}
