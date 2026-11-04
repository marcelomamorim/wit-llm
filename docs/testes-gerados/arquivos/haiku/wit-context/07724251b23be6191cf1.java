/*
 * @test
 * @summary Tests public behavior of Charset.isSupported for known, unknown and null charset names
 * @run main CharsetWitupTest
 */

import java.nio.charset.Charset;

public class CharsetWitupTest {
    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        check(Charset.isSupported("UTF-8"), "UTF-8 should be supported");
        check(Charset.isSupported("utf-8"), "lowercase utf-8 should be supported");
        check(Charset.isSupported("ISO-8859-1"), "ISO-8859-1 should be supported");
        check(!Charset.isSupported("no-such-charset-witup"), "unknown charset should not be supported");

        boolean thrown = false;
        try {
            Charset.isSupported(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        check(thrown, "null charset name should throw IllegalArgumentException");
    }
}
