/*
 * @test
 * @summary Deterministic tests for java.net.URLDecoder.decode(String, String) covering normal and exceptional cases
 * @run main URLDecoderWitupTest
 */

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.charset.IllegalCharsetNameException;
import java.io.UnsupportedEncodingException;

public class URLDecoderWitupTest {
    public static void main(String[] args) throws Exception {
        testDecodeValidEncoding();
        testDecodeEmptyEncodingThrows();
        testDecodeInvalidEncodingThrows();
        testDecodeUnsupportedEncodingThrows();
    }

    static void testDecodeValidEncoding() throws Exception {
        // Using UTF-8 encoding, decode a simple encoded string
        String encoded = "Hello+World%21"; // + -> space, %21 -> !
        String decoded = URLDecoder.decode(encoded, "UTF-8");
        if (!"Hello World!".equals(decoded)) {
            throw new AssertionError("Expected 'Hello World!' but got '" + decoded + "'");
        }

        // Using ISO-8859-1 encoding, decode a string with no special chars
        String plain = "simpleString";
        String decodedPlain = URLDecoder.decode(plain, "ISO-8859-1");
        if (!plain.equals(decodedPlain)) {
            throw new AssertionError("Expected '" + plain + "' but got '" + decodedPlain + "'");
        }
    }

    static void testDecodeEmptyEncodingThrows() {
        // According to source, empty encoding string throws UnsupportedEncodingException
        try {
            URLDecoder.decode("anyString", "");
            throw new AssertionError("Expected UnsupportedEncodingException for empty encoding string");
        } catch (UnsupportedEncodingException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Expected UnsupportedEncodingException but got " + e);
        }
    }

    static void testDecodeInvalidEncodingThrows() {
        // Encoding name with illegal charset name should throw UnsupportedEncodingException
        String illegalCharset = "*invalid-charset*";
        try {
            URLDecoder.decode("test", illegalCharset);
            throw new AssertionError("Expected UnsupportedEncodingException for illegal charset name");
        } catch (UnsupportedEncodingException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Expected UnsupportedEncodingException but got " + e);
        }
    }

    static void testDecodeUnsupportedEncodingThrows() {
        // Encoding name that is syntactically valid but unsupported should throw UnsupportedEncodingException
        String unsupportedCharset = "Unsupported-Charset-XYZ";
        try {
            URLDecoder.decode("test", unsupportedCharset);
            throw new AssertionError("Expected UnsupportedEncodingException for unsupported charset");
        } catch (UnsupportedEncodingException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Expected UnsupportedEncodingException but got " + e);
        }
    }
}
