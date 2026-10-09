/*
 * @test
 * @summary Basic tests for java.net.URLDecoder.decode(String, Charset)
 * @run main URLDecoderWitupTest_P9d77f5192381
 */

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class URLDecoderWitupTest_P9d77f5192381 {
    public static void main(String[] args) {
        testNullCharset();
        testNoChange();
        testPlusToSpace();
        testPercentDecoding();
        testMultiplePercentDecoding();
        testIncompletePercentEncoding();
        testIllegalHexCharacters();
    }

    static void testNullCharset() {
        try {
            URLDecoder.decode("abc", null);
            throw new RuntimeException("Expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            // expected
            if (!"Charset".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message: " + e.getMessage());
            }
        }
    }

    static void testNoChange() {
        String input = "abcXYZ012-_*.";
        Charset cs = StandardCharsets.UTF_8;
        String result = URLDecoder.decode(input, cs);
        if (!input.equals(result)) {
            throw new RuntimeException("Expected no change for input without special chars");
        }
    }

    static void testPlusToSpace() {
        String input = "a+b+c";
        Charset cs = StandardCharsets.UTF_8;
        String expected = "a b c";
        String result = URLDecoder.decode(input, cs);
        if (!expected.equals(result)) {
            throw new RuntimeException("Expected plus signs to be decoded to spaces");
        }
    }

    static void testPercentDecoding() {
        String input = "%41%42%43"; // ABC
        Charset cs = StandardCharsets.UTF_8;
        String expected = "ABC";
        String result = URLDecoder.decode(input, cs);
        if (!expected.equals(result)) {
            throw new RuntimeException("Expected percent encoded ABC to decode to ABC");
        }
    }

    static void testMultiplePercentDecoding() {
        String input = "Hello%20World%21"; // Hello World!
        Charset cs = StandardCharsets.UTF_8;
        String expected = "Hello World!";
        String result = URLDecoder.decode(input, cs);
        if (!expected.equals(result)) {
            throw new RuntimeException("Expected percent encoded spaces and exclamation to decode");
        }
    }

    static void testIncompletePercentEncoding() {
        String input = "abc%";
        Charset cs = StandardCharsets.UTF_8;
        try {
            URLDecoder.decode(input, cs);
            throw new RuntimeException("Expected IllegalArgumentException for incomplete percent encoding");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().contains("Incomplete trailing escape")) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    static void testIllegalHexCharacters() {
        String input = "abc%2G"; // 'G' is not a hex digit
        Charset cs = StandardCharsets.UTF_8;
        try {
            URLDecoder.decode(input, cs);
            throw new RuntimeException("Expected IllegalArgumentException for illegal hex characters");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().contains("Illegal hex characters")) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
