/*
 * @test
 * @summary X500Name.getEncoded returns a defensive copy of a stable DER encoding
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main X500NameWitupTest
 */

import java.util.Arrays;
import sun.security.util.DerValue;
import sun.security.x509.X500Name;

public class X500NameWitupTest {

    private static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    public static void main(String[] args) throws Exception {
        X500Name name = new X500Name("CN=Duke, OU=JavaSoft, O=Sun, C=US");

        byte[] enc1 = name.getEncoded();
        byte[] enc2 = name.getEncoded();

        if (enc1 == enc2) {
            fail("getEncoded must return a new array on each call");
        }
        if (!Arrays.equals(enc1, enc2)) {
            fail("repeated getEncoded calls must return equal content");
        }
        if (enc1.length == 0 || enc1[0] != 0x30) {
            fail("DER encoding must start with SEQUENCE tag 0x30");
        }

        enc1[0] ^= 0x01;

        byte[] enc3 = name.getEncoded();
        if (!Arrays.equals(enc2, enc3)) {
            fail("mutating a returned array must not change the encoding");
        }
        if (enc3[0] != 0x30) {
            fail("encoding was corrupted by external mutation");
        }

        X500Name other = new X500Name("CN=Duke, OU=JavaSoft, O=Sun, C=US");
        if (!Arrays.equals(enc3, other.getEncoded())) {
            fail("same distinguished name must yield the same encoding");
        }

        X500Name roundTrip = new X500Name(new DerValue(enc3));
        if (!Arrays.equals(enc3, roundTrip.getEncoded())) {
            fail("round trip through DerValue must preserve the encoding");
        }
    }
}
