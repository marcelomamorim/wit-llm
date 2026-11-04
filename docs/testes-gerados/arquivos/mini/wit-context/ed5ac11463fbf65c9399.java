/*
 * @test
 * @summary Deterministic tests for java.net.URLDecoder.decode(String, Charset)
 * @run main URLDecoderWitupTest
 */

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class URLDecoderWitupTest {
    public static void main(String[] args) {
        testNullCharset();
        testSimpleDecode();
        testPlusToSpace();
        testPercentDecoding();
        testIncompletePercentEncoding();
        testIllegalHexCharacters();
        testNoChangeReturnsSameString();
    }

    static void testNullCharset() {
        try {
            URLDecoder.decode("any", null);
            throw new RuntimeException("Expected NullPointerException for null charset");
        } catch (NullPointerException npe) {
            // expected
            if (!"Charset".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch");
            }
        }
    }

    static void testSimpleDecode() {
        String input = "abcXYZ012-_.";
        String result = URLDecoder.decode(input, StandardCharsets.UTF_8);
        if (!result.equals(input)) {
            throw new RuntimeException("Simple decode altered string");
        }
    }

    static void testPlusToSpace() {
        String input = "a+b+c";
        String expected = "a b c";
        String result = URLDecoder.decode(input, StandardCharsets.UTF_8);
        if (!expected.equals(result)) {
            throw new RuntimeException("Plus sign not decoded to space");
        }
    }

    static void testPercentDecoding() {
        // %41 is 'A', %20 is space
        String input = "%41%20%42";
        String expected = "A B";
        String result = URLDecoder.decode(input, StandardCharsets.UTF_8);
        if (!expected.equals(result)) {
            throw new RuntimeException("Percent decoding failed: expected '" + expected + "' but got '" + result + "'");
        }
    }

    static void testIncompletePercentEncoding() {
        String input = "abc%";
        try {
            URLDecoder.decode(input, StandardCharsets.UTF_8);
            throw new RuntimeException("Expected IllegalArgumentException for incomplete trailing escape");
        } catch (IllegalArgumentException iae) {
            if (!iae.getMessage().contains("Incomplete trailing escape")) {
                throw new RuntimeException("IllegalArgumentException message mismatch for incomplete escape");
            }
        }
    }

    static void testIllegalHexCharacters() {
        String input = "abc%2G"; // 'G' is not a hex digit
        try {
            URLDecoder.decode(input, StandardCharsets.UTF_8);
            throw new RuntimeException("Expected IllegalArgumentException for illegal hex characters");
        } catch (IllegalArgumentException iae) {
            if (!iae.getMessage().contains("Illegal hex characters")) {
                throw new RuntimeException("IllegalArgumentException message mismatch for illegal hex characters");
            }
        }
    }

    static void testNoChangeReturnsSameString() {
        String input = "simpleString123";
        String result = URLDecoder.decode(input, StandardCharsets.UTF_8);
        if (result != input && !result.equals(input)) {
            throw new RuntimeException("Decode should return original string if no changes needed");
        }
    }
}
