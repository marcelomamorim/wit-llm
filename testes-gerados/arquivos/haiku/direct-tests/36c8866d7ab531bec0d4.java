/*
 * @test
 * @summary Verify X500Name.getEncodedInternal caching, DER tag, round-trip decoding and distinct encodings
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main X500NameWitupTest
 */

import java.util.Arrays;
import sun.security.x509.X500Name;
import sun.security.util.DerValue;

public class X500NameWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        X500Name name = new X500Name("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");

        byte[] first = name.getEncodedInternal();
        byte[] second = name.getEncodedInternal();
        check(first == second, "encoded form should be cached and reused");
        check(first.length > 0, "encoded form should not be empty");
        check(first[0] == DerValue.tag_Sequence, "encoded form should start with SEQUENCE tag");

        X500Name decoded = new X500Name(new DerValue(first));
        check(decoded.equals(name), "decoded name should equal original name");
        check(Arrays.equals(decoded.getEncodedInternal(), first),
              "re-encoding decoded name should yield identical bytes");

        X500Name other = new X500Name("CN=Other, C=US");
        check(!Arrays.equals(other.getEncodedInternal(), first),
              "different names should have different encodings");
    }
}
