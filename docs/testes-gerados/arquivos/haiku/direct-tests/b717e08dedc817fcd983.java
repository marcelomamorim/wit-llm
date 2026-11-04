/*
 * @test
 * @summary PropertyResourceBundleCharset.newEncoder must throw UnsupportedOperationException
 * @modules java.base/sun.util
 * @run main PropertyResourceBundleCharsetWitupTest
 */

import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import sun.util.PropertyResourceBundleCharset;

public class PropertyResourceBundleCharsetWitupTest {

    public static void main(String[] args) throws Exception {
        check(new PropertyResourceBundleCharset("X-PRBC-TEST", null));
        check(new PropertyResourceBundleCharset(true));
        check(new PropertyResourceBundleCharset(false));
    }

    static void check(PropertyResourceBundleCharset cs) {
        boolean thrown = false;
        try {
            CharsetEncoder enc = cs.newEncoder();
            if (enc == null) {
                throw new RuntimeException("newEncoder returned null");
            }
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected UnsupportedOperationException from newEncoder");
        }
        CharsetDecoder dec = cs.newDecoder();
        if (dec == null) {
            throw new RuntimeException("newDecoder returned null");
        }
    }
}
