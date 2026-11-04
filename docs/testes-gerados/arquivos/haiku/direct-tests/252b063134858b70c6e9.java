/*
 * @test
 * @summary DNSName(DerValue) accepts IA5String DER values and rejects other tags
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main DNSNameWitupTest
 */

import java.io.IOException;

import sun.security.util.DerValue;
import sun.security.x509.DNSName;

public class DNSNameWitupTest {

    public static void main(String[] args) throws Exception {
        testValidIA5StringIsAccepted();
        testNonIA5StringTagIsRejected();
        testDerConstructorDoesNotApplyStringConstructorBlankCheck();
    }

    static void testValidIA5StringIsAccepted() throws Exception {
        byte[] encoded = {0x16, 0x0B,
            'e', 'x', 'a', 'm', 'p', 'l', 'e', '.', 'c', 'o', 'm'};
        DNSName dns = new DNSName(new DerValue(encoded));
        if (dns == null) {
            throw new AssertionError("DNSName must be created from IA5String DER value");
        }
    }

    static void testNonIA5StringTagIsRejected() throws Exception {
        byte[] encoded = {0x0C, 0x0B,
            'e', 'x', 'a', 'm', 'p', 'l', 'e', '.', 'c', 'o', 'm'};
        try {
            new DNSName(new DerValue(encoded));
            throw new AssertionError("expected IOException for non-IA5String tag");
        } catch (IOException expected) {
            // expected: getIA5String rejects a non-IA5String tag
        }
    }

    static void testDerConstructorDoesNotApplyStringConstructorBlankCheck() throws Exception {
        byte[] encoded = {0x16, 0x03, 'a', ' ', 'b'};
        DNSName dns = new DNSName(new DerValue(encoded));
        if (dns == null) {
            throw new AssertionError("DER constructor is expected to assign the value without validation");
        }
    }
}
