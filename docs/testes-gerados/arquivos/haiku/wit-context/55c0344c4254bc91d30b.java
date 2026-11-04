/*
 * @test
 * @summary Verify CRLNumberExtension.get(String) returns the CRL number for "value" (case-insensitive) and throws IOException for other names
 * @modules java.base/sun.security.x509
 * @run main CRLNumberExtensionWitupTest
 */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        CRLNumberExtension ext = new CRLNumberExtension(42);
        check(BigInteger.valueOf(42).equals(ext.get("value")), "exact name 'value'");
        check(BigInteger.valueOf(42).equals(ext.get("VALUE")), "upper-case name 'VALUE'");
        check(BigInteger.valueOf(42).equals(ext.get("Value")), "mixed-case name 'Value'");

        CRLNumberExtension zero = new CRLNumberExtension(0);
        check(BigInteger.ZERO.equals(zero.get("value")), "zero CRL number");

        CRLNumberExtension negative = new CRLNumberExtension(-7);
        check(BigInteger.valueOf(-7).equals(negative.get("value")), "negative CRL number");

        BigInteger large = new BigInteger("123456789012345678901234567890");
        CRLNumberExtension bigExt = new CRLNumberExtension(large);
        check(large.equals(bigExt.get("value")), "BigInteger constructor value");

        expectIOException(ext, "other");
        expectIOException(ext, "");
        expectIOException(ext, "crlnumber");
        expectIOException(ext, "value ");
        expectIOException(ext, " value");
    }

    private static void expectIOException(CRLNumberExtension ext, String name) {
        try {
            ext.get(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException for name: '" + name + "'");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
