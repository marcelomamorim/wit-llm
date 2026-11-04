/*
 * @test
 * @summary Checks that X500Name.getEncoded() returns a fresh DER copy that round-trips and is not affected by caller mutation
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main X500NameWitupTest
 */

import sun.security.x509.X500Name;
import sun.security.util.DerValue;
import java.util.Arrays;

public class X500NameWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        X500Name name = new X500Name("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");

        byte[] enc1 = name.getEncoded();
        check(enc1 != null, "getEncoded returned null");
        check(enc1.length > 0, "getEncoded returned empty array");
        check((enc1[0] & 0xff) == 0x30, "encoding does not start with DER SEQUENCE tag");

        byte[] enc2 = name.getEncoded();
        check(Arrays.equals(enc1, enc2), "repeated getEncoded calls differ");
        check(enc1 != enc2, "getEncoded returned the same array instance");

        enc1[0] = 0;
        byte[] enc3 = name.getEncoded();
        check((enc3[0] & 0xff) == 0x30, "caller mutation affected subsequent encoding");

        X500Name parsed = new X500Name(new DerValue(enc3));
        check(parsed.toString().equals(name.toString()), "round-trip changed the name");
        check(Arrays.equals(parsed.getEncoded(), enc3), "re-encoding after round-trip differs");
    }
}
