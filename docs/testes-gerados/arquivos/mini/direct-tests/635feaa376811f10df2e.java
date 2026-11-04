/*
 * @test
 * @summary Basic tests for java.net.URLDecoder.decode(String,String) method
 * @run main URLDecoderWitupTest
 */

import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;

public class URLDecoderWitupTest {
    public static void main(String[] args) throws Exception {
        testValidEncoding();
        testEmptyEncodingThrows();
        testUnsupportedEncodingThrows();
        testIllegalCharsetNameThrows();
    }

    static void testValidEncoding() throws Exception {
        // Simple ASCII string, encoding UTF-8
        String input = "Hello+World%21";
        String expected = "Hello World!";
        String result = URLDecoder.decode(input, "UTF-8");
        if (!expected.equals(result)) {
            throw new RuntimeException("Failed decoding ASCII string: expected '" + expected + "' but got '" + result + "'");
        }

        // String with multibyte UTF-8 encoded character: %C3%A9 = 'é'
        input = "caf%C3%A9";
        expected = "café";
        result = URLDecoder.decode(input, "UTF-8");
        if (!expected.equals(result)) {
            throw new RuntimeException("Failed decoding UTF-8 string: expected '" + expected + "' but got '" + result + "'");
        }
    }

    static void testEmptyEncodingThrows() {
        try {
            URLDecoder.decode("test", "");
            throw new RuntimeException("Expected UnsupportedEncodingException for empty encoding string");
        } catch (UnsupportedEncodingException e) {
            // Expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for empty encoding string", e);
        }
    }

    static void testUnsupportedEncodingThrows() {
        try {
            URLDecoder.decode("test", "NoSuchEncoding123");
            throw new RuntimeException("Expected UnsupportedEncodingException for unsupported encoding");
        } catch (UnsupportedEncodingException e) {
            // Expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for unsupported encoding", e);
        }
    }

    static void testIllegalCharsetNameThrows() {
        try {
            // Illegal charset name with invalid characters
            URLDecoder.decode("test", "*illegal-charset*");
            throw new RuntimeException("Expected UnsupportedEncodingException for illegal charset name");
        } catch (UnsupportedEncodingException e) {
            // Expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for illegal charset name", e);
        }
    }
}
