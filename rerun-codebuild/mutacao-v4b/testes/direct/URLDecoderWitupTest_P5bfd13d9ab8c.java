/* @test
   @summary Tests URLDecoder.decode(String s, Charset charset) for correct decoding.
   @run main URLDecoderWitupTest_P5bfd13d9ab8c
 */
import java.net.URLDecoder;
import java.nio.charset.Charset;
public class URLDecoderWitupTest_P5bfd13d9ab8c {
    public static void main(String[] args) {
        testSimpleDecoding();
        testPlusToSpace();
        testPercentEncoding();
        testIncompletePercentEncoding();
        testNegativeHexValue();
        testIllegalHexCharacters();
        testIncompleteTrailingEscape();
    }

    static void testSimpleDecoding() {
        String encoded = "Hello%20World";
        String expected = "Hello World";
        String result = URLDecoder.decode(encoded, Charset.forName("UTF-8"));
        if (!result.equals(expected)) {
            throw new AssertionError("Simple decoding failed: expected '" + expected + "', got '" + result + "'");
        }
    }

    static void testPlusToSpace() {
        String encoded = "Hello+World";
        String expected = "Hello World";
        String result = URLDecoder.decode(encoded, Charset.forName("UTF-8"));
        if (!result.equals(expected)) {
            throw new AssertionError("Plus to space decoding failed: expected '" + expected + "', got '" + result + "'");
        }
    }

    static void testPercentEncoding() {
        String encoded = "%48%65%6C%6C%6F"; // "Hello" in hex
        String expected = "Hello";
        String result = URLDecoder.decode(encoded, Charset.forName("UTF-8"));
        if (!result.equals(expected)) {
            throw new AssertionError("Percent encoding decoding failed: expected '" + expected + "', got '" + result + "'");
        }
    }

    static void testIncompletePercentEncoding() {
        String encoded = "Hello%2"; // incomplete percent
        String expected = "Hello%2";
        String result = URLDecoder.decode(encoded, Charset.forName("UTF-8"));
        if (!result.equals(expected)) {
            throw new AssertionError("Incomplete percent encoding should return original string: expected '" + expected + "', got '" + result + "'");
        }
    }

    static void testNegativeHexValue() {
        String encoded = "%-1A";
        try {
            URLDecoder.decode(encoded, Charset.forName("UTF-8"));
            throw new AssertionError("Expected IllegalArgumentException for negative hex value");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testIllegalHexCharacters() {
        String encoded = "%ZZ";
        try {
            URLDecoder.decode(encoded, Charset.forName("UTF-8"));
            throw new AssertionError("Expected IllegalArgumentException for illegal hex characters");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testIncompleteTrailingEscape() {
        String encoded = "%";
        try {
            URLDecoder.decode(encoded, Charset.forName("UTF-8"));
            throw new AssertionError("Expected IllegalArgumentException for incomplete trailing escape");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
