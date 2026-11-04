/*
 * @test
 * @summary Tests Charset.forName for null, unsupported and known charset names
 * @run main CharsetWitupTest
 */

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class CharsetWitupTest {

    public static void main(String[] args) throws Exception {
        testNullNameThrowsIllegalArgument();
        testUnsupportedNameThrowsUnsupportedCharset();
        testKnownNameReturnsCharset();
        testAliasReturnsSameCharset();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullNameThrowsIllegalArgument() {
        try {
            Charset.forName(null);
            throw new RuntimeException("forName(null) did not throw");
        } catch (UnsupportedCharsetException e) {
            throw new RuntimeException("forName(null) threw UnsupportedCharsetException", e);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testUnsupportedNameThrowsUnsupportedCharset() {
        String name = "x-witup-no-such-charset";
        try {
            Charset.forName(name);
            throw new RuntimeException("forName of unsupported name did not throw");
        } catch (UnsupportedCharsetException e) {
            check(name.equals(e.getCharsetName()),
                  "getCharsetName mismatch: " + e.getCharsetName());
        }
    }

    static void testKnownNameReturnsCharset() {
        Charset cs = Charset.forName("UTF-8");
        check(cs != null, "forName(UTF-8) returned null");
        check("UTF-8".equals(cs.name()), "unexpected name: " + cs.name());
    }

    static void testAliasReturnsSameCharset() {
        Charset canonical = Charset.forName("UTF-8");
        Charset alias = Charset.forName("UTF8");
        check(canonical.equals(alias), "alias UTF8 did not resolve to UTF-8");
    }
}
