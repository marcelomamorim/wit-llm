/* @test    @summary Test URLDecoder.decode with various inputs and charsets    @run main URLDecoderWitupTest */

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class URLDecoderWitupTest {

    public static void main(String[] args) {
        testDecodeSimple();
        testDecodePlus();
        testDecodePercentEncoding();
        testDecodeIncompletePercent();
        testDecodeInvalidHex();
        testDecodeEmptyString();
        testDecodeNullCharset();
        testDecodeDifferentCharsets();
    }

    static void testDecodeSimple() {
        String encoded = "Hello%20World";
        String expected = "Hello World";
        String result = URLDecoder.decode(encoded, StandardCharsets.UTF_8);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed testDecodeSimple: expected '" + expected + "', got '" + result + "'");
        }
    }

    static void testDecodePlus() {
        String encoded = "Hello+World";
        String expected = "Hello World";
        String result = URLDecoder.decode(encoded, StandardCharsets.UTF_8);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed testDecodePlus: expected '" + expected + "', got '" + result + "'");
        }
    }

    static void testDecodePercentEncoding() {
        String encoded = "%E2%9C%93"; // check mark in UTF-8
        String expected = "✓";
        String result = URLDecoder.decode(encoded, StandardCharsets.UTF_8);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed testDecodePercentEncoding: expected '" + expected + "', got '" + result + "'");
        }
    }

    static void testDecodeIncompletePercent() {
        String encoded = "Incomplete%";
        try {
            URLDecoder.decode(encoded, StandardCharsets.UTF_8);
            throw new AssertionError("Expected IllegalArgumentException for incomplete percent encoding");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testDecodeInvalidHex() {
        String encoded = "%ZZ";
        try {
            URLDecoder.decode(encoded, StandardCharsets.UTF_8);
            throw new AssertionError("Expected IllegalArgumentException for invalid hex in percent encoding");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testDecodeEmptyString() {
        String encoded = "";
        String result = URLDecoder.decode(encoded, StandardCharsets.UTF_8);
        if (!result.equals("") ) {
            throw new AssertionError("Failed testDecodeEmptyString: expected empty string, got '" + result + "'");
        }
    }

    static void testDecodeNullCharset() {
        String encoded = "Hello%20World";
        try {
            URLDecoder.decode(encoded, null);
            throw new AssertionError("Expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testDecodeDifferentCharsets() {
        String encoded = "%E2%9C%93"; // check mark in UTF-8
        String resultUtf8 = URLDecoder.decode(encoded, StandardCharsets.UTF_8);
        String resultIso8859 = URLDecoder.decode(encoded, Charset.forName("ISO-8859-1"));
        if (resultUtf8.equals(resultIso8859)) {
            throw new AssertionError("Expected different results for different charsets");
        }
    }
}
