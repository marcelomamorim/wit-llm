/*
 * @test
 * @summary WITUP-generated check of the public CRLNumberExtension(BigInteger) constructor: criticality, OID and DER-encoded value
 * @modules java.base/sun.security.x509
 * @run main CRLNumberExtensionWitupTest
 */

import java.math.BigInteger;
import java.util.Arrays;

import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        CRLNumberExtension ext = new CRLNumberExtension(BigInteger.valueOf(5));
        check(!ext.isCritical(), "extension must be non-critical");
        check("2.5.29.20".equals(ext.getExtensionId().toString()), "unexpected extension OID");
        check(Arrays.equals(ext.getExtensionValue(), new byte[] {0x02, 0x01, 0x05}),
              "unexpected DER value for 5");

        CRLNumberExtension big = new CRLNumberExtension(new BigInteger("128"));
        check(Arrays.equals(big.getExtensionValue(), new byte[] {0x02, 0x02, 0x00, (byte) 0x80}),
              "unexpected DER value for 128");

        CRLNumberExtension nul = new CRLNumberExtension((BigInteger) null);
        check(nul.getExtensionValue() == null, "null CRL number must yield no encoded value");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
