/* @test
   @summary Testa o método decode(String s, Charset charset) da classe java.net.URLDecoder.
   @run main URLDecoderWitupTest_P5bfd13d9ab8c
 */
import java.net.URLDecoder;
import java.nio.charset.Charset;
public class URLDecoderWitupTest_P5bfd13d9ab8c {
    public static void main(String[] args) {
        testDecodeNullCharset();
        testDecodeSimpleString();
        testDecodeWithPercentEncoding();
        testDecodeWithPlusSign();
        testDecodeIncompletePercentEncoding();
        testDecodeIllegalHex();
    }

    static void testDecodeNullCharset() {
        String input = "hello%20world";
        try {
            URLDecoder.decode(input, null);
            throw new AssertionError("Expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testDecodeSimpleString() {
        String input = "hello+world";
        String result = URLDecoder.decode(input, Charset.forName("UTF-8"));
        if (!"hello world".equals(result)) {
            throw new AssertionError("Unexpected result: " + result);
        }
    }

    static void testDecodeWithPercentEncoding() {
        String input = "hello%20world";
        String result = URLDecoder.decode(input, Charset.forName("UTF-8"));
        if (!"hello world".equals(result)) {
            throw new AssertionError("Unexpected result: " + result);
        }
    }

    static void testDecodeWithPlusSign() {
        String input = "a+b+c";
        String result = URLDecoder.decode(input, Charset.forName("UTF-8"));
        if (!"a b c".equals(result)) {
            throw new AssertionError("Unexpected result: " + result);
        }
    }

    static void testDecodeIncompletePercentEncoding() {
        String input = "hello%2";
        try {
            URLDecoder.decode(input, Charset.forName("UTF-8"));
            throw new AssertionError("Expected IllegalArgumentException for incomplete percent encoding");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testDecodeIllegalHex() {
        String input = "hello%zz";
        try {
            URLDecoder.decode(input, Charset.forName("UTF-8"));
            throw new AssertionError("Expected IllegalArgumentException for illegal hex characters");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
