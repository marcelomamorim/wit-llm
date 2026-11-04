/*
 * @test
 * @summary Verify observable behavior of the CRLNumberExtension(int) constructor
 * @modules java.base/sun.security.x509
 * @run main CRLNumberExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static byte[] encodeOf(CRLNumberExtension ext) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ext.encode(out);
        return out.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        CRLNumberExtension zero = new CRLNumberExtension(0);
        check(zero != null, "constructor returned null");
        check(CRLNumberExtension.NAME.equals("CRLNumber"), "unexpected NAME constant");
        check(encodeOf(zero).length > 0, "zero extension encoded to empty array");

        CRLNumberExtension negative = new CRLNumberExtension(-1);
        check(encodeOf(negative).length > 0, "negative extension encoded to empty array");

        CRLNumberExtension max = new CRLNumberExtension(Integer.MAX_VALUE);
        check(encodeOf(max).length > 0, "MAX_VALUE extension encoded to empty array");

        CRLNumberExtension min = new CRLNumberExtension(Integer.MIN_VALUE);
        check(encodeOf(min).length > 0, "MIN_VALUE extension encoded to empty array");

        int[] samples = {0, 1, 42, -7, Integer.MAX_VALUE, Integer.MIN_VALUE};
        for (int v : samples) {
            byte[] fromInt = encodeOf(new CRLNumberExtension(v));
            byte[] fromBig = encodeOf(new CRLNumberExtension(BigInteger.valueOf(v)));
            check(java.util.Arrays.equals(fromInt, fromBig),
                  "int constructor differs from BigInteger.valueOf(" + v + ")");
        }

        byte[] a = encodeOf(new CRLNumberExtension(1));
        byte[] b = encodeOf(new CRLNumberExtension(2));
        check(!java.util.Arrays.equals(a, b), "different CRL numbers encoded identically");
    }
}
