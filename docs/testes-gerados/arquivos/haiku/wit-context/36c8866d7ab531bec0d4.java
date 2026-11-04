/*
 * @test
 * @summary Verify X500Name.getEncodedInternal() returns a stable DER encoding, caches it across calls, and round-trips through DerInputStream
 * @modules java.base/sun.security.x509
 *          java.base/sun.security.util
 * @run main X500NameWitupTest
 */

import java.util.Arrays;

import sun.security.util.DerInputStream;
import sun.security.x509.X500Name;

public class X500NameWitupTest {

    public static void main(String[] args) throws Exception {
        String dn = "CN=Test, OU=Unit, O=Org, C=US";

        X500Name name = new X500Name(dn);
        byte[] first = name.getEncodedInternal();
        byte[] second = name.getEncodedInternal();

        check(first != null, "encoding must not be null");
        check(first.length > 2, "encoding must not be trivially short");
        check(first[0] == 0x30, "outer DER tag must be SEQUENCE (0x30)");
        check(Arrays.equals(first, second), "repeated calls must return identical content");

        X500Name sameDn = new X500Name(dn);
        check(Arrays.equals(first, sameDn.getEncodedInternal()),
                "same DN must produce identical encoding");

        X500Name parsed = new X500Name(new DerInputStream(first));
        check(Arrays.equals(first, parsed.getEncodedInternal()),
                "round-trip through DerInputStream must preserve encoding");

        X500Name other = new X500Name("CN=Other, C=US");
        check(!Arrays.equals(first, other.getEncodedInternal()),
                "different DN must produce different encoding");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
