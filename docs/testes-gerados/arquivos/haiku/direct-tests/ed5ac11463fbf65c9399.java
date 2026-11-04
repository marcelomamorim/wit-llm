/*
 * @test
 * @summary Behavioral tests for URLDecoder.decode(String, Charset) covering plus, escapes, charsets, null charset and malformed escapes
 * @run main URLDecoderWitupTest
 */

import java.net.URLDecoder;
import java.nio.charset.Charset;

public class URLDecoderWitupTest {

    static final Charset UTF8 = Charset.forName("UTF-8");
    static final Charset LATIN1 = Charset.forName("ISO-8859-1");
    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAIL: " + msg);
        }
    }

    static void expectIAE(String s) {
        try {
            String r = URLDecoder.decode(s, UTF8);
            failures++;
            System.err.println("FAIL: no IllegalArgumentException for \"" + s + "\", got \"" + r + "\"");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    public static void main(String[] args) throws Exception {
        check("a b".equals(URLDecoder.decode("a+b", UTF8)), "plus must decode to space");
        check("abc".equals(URLDecoder.decode("abc", UTF8)), "plain string must be unchanged");
        check("".equals(URLDecoder.decode("", UTF8)), "empty string must decode to empty");
        check("AB".equals(URLDecoder.decode("%41%42", UTF8)), "%41%42 must decode to AB");
        check("a b".equals(URLDecoder.decode("a%20b", UTF8)), "%20 must decode to space");
        check("\u00e9".equals(URLDecoder.decode("%C3%A9", UTF8)), "UTF-8 multibyte sequence must decode");
        check("\u00c3\u00a9".equals(URLDecoder.decode("%C3%A9", LATIN1)), "ISO-8859-1 must decode bytes individually");
        check("x y".equals(URLDecoder.decode("x%20y", LATIN1)), "escape with latin1 charset");

        expectIAE("%4");
        expectIAE("%zz");
        expectIAE("%-1");
        expectIAE("%");

        try {
            URLDecoder.decode("a", null);
            failures++;
            System.err.println("FAIL: null charset must throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
