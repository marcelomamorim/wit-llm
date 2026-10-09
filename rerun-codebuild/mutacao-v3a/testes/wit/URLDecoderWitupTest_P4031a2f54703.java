/*
 * @test
 * @summary Test for java.net.URLDecoder.decode(String s, Charset charset)
 * @run main URLDecoderWitupTest_P4031a2f54703
 */

import java.net.URLDecoder;
import java.nio.charset.Charset;

public class URLDecoderWitupTest_P4031a2f54703 {
    public static void main(String[] args) {
        testDecodeWithNullCharset();
        testDecodeWithValidEncoding();
        testDecodeWithPercentEncoding();
        testDecodeWithPlusSigns();
        testDecodeWithIncompletePercentEncoding();
        testDecodeWithInvalidHex();
    }

    public static void testDecodeWithNullCharset() {
        String input = "test";
        try {
            URLDecoder.decode(input, null);
            System.err.println("Failed: Expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testDecodeWithValidEncoding() {
        String input = "hello+world";
        String expected = "hello world";
        String result = URLDecoder.decode(input, Charset.forName("UTF-8"));
        if (!result.equals(expected)) {
            System.err.println("Failed: decode with valid encoding, got: " + result);
        }
    }

    public static void testDecodeWithPercentEncoding() {
        String input = "hello%20world";
        String expected = "hello world";
        String result = URLDecoder.decode(input, Charset.forName("UTF-8"));
        if (!result.equals(expected)) {
            System.err.println("Failed: decode with percent encoding, got: " + result);
        }
    }

    public static void testDecodeWithPlusSigns() {
        String input = "a+b+c";
        String expected = "a b c";
        String result = URLDecoder.decode(input, Charset.forName("UTF-8"));
        if (!result.equals(expected)) {
            System.err.println("Failed: decode with plus signs, got: " + result);
        }
    }

    public static void testDecodeWithIncompletePercentEncoding() {
        String input = "hello%2";
        try {
            URLDecoder.decode(input, Charset.forName("UTF-8"));
            System.err.println("Failed: Expected IllegalArgumentException for incomplete percent encoding");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    public static void testDecodeWithInvalidHex() {
        String input = "hello%zz";
        try {
            URLDecoder.decode(input, Charset.forName("UTF-8"));
            System.err.println("Failed: Expected IllegalArgumentException for invalid hex characters");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
