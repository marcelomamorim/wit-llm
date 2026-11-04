/*
 * @test
 * @summary Verifies the public int constructor of CRLNumberExtension accepts representative values and exposes its public NAME constant
 * @modules java.base/sun.security.x509
 * @run main CRLNumberExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    public static void main(String[] args) throws IOException {
        check("CRLNumber".equals(CRLNumberExtension.NAME), "NAME constant changed");
        check("value".equals(CRLNumberExtension.NUMBER), "NUMBER constant changed");

        int[] values = {0, 1, -1, 42, Integer.MAX_VALUE, Integer.MIN_VALUE};
        for (int v : values) {
            CRLNumberExtension ext = new CRLNumberExtension(v);
            check(ext != null, "constructor returned null for " + v);
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
