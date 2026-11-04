/*
 * @test
 * @summary Tests URLDecoder.decode with valid and invalid encodings
 * @run main URLDecoderWitupTest
 */

import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;

public class URLDecoderWitupTest {

    public static void main(String[] args) {
        testDecodeValidEncoding();
        testDecodeEmptyEncoding();
        testDecodeUnsupportedEncoding();
    }

    static void testDecodeValidEncoding() {
        String encoded = "hello%20world";
        String encoding = "UTF-8";
        try {
            String result = URLDecoder.decode(encoded, encoding);
            if (!"hello world".equals(result)) {
                throw new AssertionError("Expected 'hello world', got: " + result);
            }
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("Unexpected exception for valid encoding", e);
        }
    }

    static void testDecodeEmptyEncoding() {
        String encoded = "hello";
        String encoding = "";
        try {
            URLDecoder.decode(encoded, encoding);
            throw new AssertionError("Expected UnsupportedEncodingException for empty encoding");
        } catch (UnsupportedEncodingException e) {
            // expected
        }
    }

    static void testDecodeUnsupportedEncoding() {
        String encoded = "hello";
        String encoding = "unsupported-charset";
        try {
            URLDecoder.decode(encoded, encoding);
            throw new AssertionError("Expected UnsupportedEncodingException for unsupported encoding");
        } catch (UnsupportedEncodingException e) {
            // expected
        }
    }
}
