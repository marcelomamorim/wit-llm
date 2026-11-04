/*
 * @test
 * @summary Witup-style tests for CRLNumberExtension.get(String)
 * @modules java.base/sun.security.x509
 * @run main CRLNumberExtensionWitupTest
 */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        CRLNumberExtension ext = new CRLNumberExtension(42);
        check(BigInteger.valueOf(42).equals(ext.get("value")), "get(value) should return 42");
        check(BigInteger.valueOf(42).equals(ext.get("VALUE")), "get(VALUE) should be case-insensitive");
        check(BigInteger.valueOf(42).equals(ext.get("Value")), "get(Value) should be case-insensitive");

        BigInteger big = new BigInteger("123456789012345678901234567890");
        CRLNumberExtension ext2 = new CRLNumberExtension(big);
        check(big.equals(ext2.get("value")), "get(value) should return the BigInteger passed in");

        expectIOException(ext, "CRLNumber");
        expectIOException(ext, "foo");
        expectIOException(ext, "");
    }

    private static void expectIOException(CRLNumberExtension ext, String name) {
        try {
            ext.get(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for attribute name: " + name);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
