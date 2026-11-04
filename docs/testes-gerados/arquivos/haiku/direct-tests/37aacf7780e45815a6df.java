/*
 * @test
 * @summary Basic observable behavior of the public CRLNumberExtension(BigInteger) constructor
 * @modules java.base/sun.security.x509
 * @run main CRLNumberExtensionWitupTest
 */

import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        check("CRLNumber".equals(CRLNumberExtension.NAME),
                "NAME constant changed");
        check("value".equals(CRLNumberExtension.NUMBER),
                "NUMBER constant changed");

        CRLNumberExtension ext = new CRLNumberExtension(BigInteger.valueOf(5));
        check(ext != null, "constructor returned null");

        CRLNumberExtension zero = new CRLNumberExtension(BigInteger.ZERO);
        check(zero != null, "zero value constructor returned null");

        CRLNumberExtension negative = new CRLNumberExtension(BigInteger.valueOf(-7));
        check(negative != null, "negative value constructor returned null");

        CRLNumberExtension nullValue = new CRLNumberExtension((BigInteger) null);
        check(nullValue != null, "null value constructor returned null");

        CRLNumberExtension fromInt = new CRLNumberExtension(3);
        check(fromInt != null, "int constructor returned null");
    }
}
