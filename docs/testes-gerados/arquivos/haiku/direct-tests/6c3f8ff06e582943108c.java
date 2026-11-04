/*
 * @test
 * @summary Basic observable behavior of Charset.forName: name normalization, alias lookup and UnsupportedCharsetException for unknown names
 * @run main CharsetWitupTest
 */

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class CharsetWitupTest {

    public static void main(String[] args) throws Exception {
        testCanonicalName();
        testCaseInsensitiveLookup();
        testAliasLookup();
        testUnsupportedName();
    }

    static void testCanonicalName() {
        Charset utf8 = Charset.forName("UTF-8");
        if (utf8 == null) {
            throw new RuntimeException("forName returned null for UTF-8");
        }
        if (!"UTF-8".equals(utf8.name())) {
            throw new RuntimeException("unexpected name: " + utf8.name());
        }
    }

    static void testCaseInsensitiveLookup() {
        Charset upper = Charset.forName("UTF-8");
        Charset lower = Charset.forName("utf-8");
        if (!upper.equals(lower)) {
            throw new RuntimeException("case-insensitive lookup returned different charset");
        }
    }

    static void testAliasLookup() {
        Charset latin1 = Charset.forName("latin1");
        if (!"ISO-8859-1".equals(latin1.name())) {
            throw new RuntimeException("alias latin1 resolved to: " + latin1.name());
        }
    }

    static void testUnsupportedName() {
        String name = "x-no-such-charset-witup";
        try {
            Charset.forName(name);
            throw new RuntimeException("expected UnsupportedCharsetException");
        } catch (UnsupportedCharsetException e) {
            if (!name.equals(e.getCharsetName())) {
                throw new RuntimeException("wrong charset name in exception: " + e.getCharsetName());
            }
        }
    }
}
