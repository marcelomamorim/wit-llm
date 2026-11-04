/*
 * @test
 * @summary Test CRLNumberExtension constructor with int parameter
 * @run main CRLNumberExtensionWitupTest
 */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {
    public static void main(String[] args) throws IOException {
        // Test with zero
        CRLNumberExtension extZero = new CRLNumberExtension(0);
        if (extZero == null) {
            throw new RuntimeException("CRLNumberExtension constructor returned null for 0");
        }

        // Test with positive int
        CRLNumberExtension extPositive = new CRLNumberExtension(12345);
        if (extPositive == null) {
            throw new RuntimeException("CRLNumberExtension constructor returned null for positive int");
        }

        // Test with negative int (allowed by BigInteger.valueOf)
        CRLNumberExtension extNegative = new CRLNumberExtension(-1);
        if (extNegative == null) {
            throw new RuntimeException("CRLNumberExtension constructor returned null for negative int");
        }

        // Test with Integer.MAX_VALUE
        CRLNumberExtension extMax = new CRLNumberExtension(Integer.MAX_VALUE);
        if (extMax == null) {
            throw new RuntimeException("CRLNumberExtension constructor returned null for Integer.MAX_VALUE");
        }

        // Test with Integer.MIN_VALUE
        CRLNumberExtension extMin = new CRLNumberExtension(Integer.MIN_VALUE);
        if (extMin == null) {
            throw new RuntimeException("CRLNumberExtension constructor returned null for Integer.MIN_VALUE");
        }

        System.out.println("All CRLNumberExtension(int) constructor tests passed.");
    }
}
