/*
 * @test
 * @summary Witup-style checks for URLDecoder.decode(String, String) observable behavior and exceptions
 * @run main URLDecoderWitupTest
 */

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class URLDecoderWitupTest {

    public static void main(String[] args) throws Exception {
        check("a+b", "UTF-8", "a b");
        check("%41%42", "UTF-8", "AB");
        check("abc", "UTF-8", "abc");
        check("%C3%A9", "UTF-8", "\u00e9");

        expectUnsupported("x", "");
        expectUnsupported("x", "NO-SUCH-CHARSET");
        expectUnsupported("x", "UTF 8");

        try {
            URLDecoder.decode("x", null);
            throw new RuntimeException("expected NullPointerException for null enc");
        } catch (NullPointerException expected) {
        }
    }

    private static void check(String s, String enc, String expected) throws Exception {
        String actual = URLDecoder.decode(s, enc);
        if (!expected.equals(actual)) {
            throw new RuntimeException("decode(" + s + "," + enc + ") = " + actual
                    + ", expected " + expected);
        }
    }

    private static void expectUnsupported(String s, String enc) {
        try {
            URLDecoder.decode(s, enc);
            throw new RuntimeException("expected UnsupportedEncodingException for enc=\"" + enc + "\"");
        } catch (UnsupportedEncodingException expected) {
        }
    }
}
