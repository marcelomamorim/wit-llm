/*
 * @test
 * @summary Behavioral tests for URLDecoder.decode(String, String) using public API only
 * @run main URLDecoderWitupTest
 */

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class URLDecoderWitupTest {

    public static void main(String[] args) throws Exception {
        check("a b", URLDecoder.decode("a+b", "UTF-8"));
        check("AB", URLDecoder.decode("%41%42", "UTF-8"));
        check("x+y", URLDecoder.decode("x%2By", "UTF-8"));
        check(" ", URLDecoder.decode("%20", "UTF-8"));
        check("", URLDecoder.decode("", "UTF-8"));
        check("abc-_.*", URLDecoder.decode("abc-_.*", "UTF-8"));
        check("\u4F60", URLDecoder.decode("%E4%BD%A0", "UTF-8"));
        check("\u00E9", URLDecoder.decode("%E9", "ISO-8859-1"));

        expectUnsupported("");
        expectUnsupported("no-such-charset-xyz");
        expectUnsupported("@@illegal@@");
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected [" + expected + "] but was [" + actual + "]");
        }
    }

    static void expectUnsupported(String enc) {
        try {
            URLDecoder.decode("abc", enc);
        } catch (UnsupportedEncodingException e) {
            return;
        }
        throw new RuntimeException("No UnsupportedEncodingException for enc=[" + enc + "]");
    }
}
