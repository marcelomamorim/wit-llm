/*
 * @test
 * @summary Test CRLNumberExtension constructor with int argument
 * @run main CRLNumberExtensionWitupTest
 */

import sun.security.x509.CRLNumberExtension;
import java.math.BigInteger;
import java.io.IOException;

public class CRLNumberExtensionWitupTest {
    public static void main(String[] args) throws IOException {
        // Test with zero
        CRLNumberExtension extZero = new CRLNumberExtension(0);
        BigInteger valZero = extZero.getNumber();
        if (!BigInteger.ZERO.equals(valZero)) {
            throw new RuntimeException("Expected 0 but got " + valZero);
        }

        // Test with positive int
        int testVal = 123456789;
        CRLNumberExtension extPos = new CRLNumberExtension(testVal);
        BigInteger valPos = extPos.getNumber();
        if (!BigInteger.valueOf(testVal).equals(valPos)) {
            throw new RuntimeException("Expected " + testVal + " but got " + valPos);
        }

        // Test with negative int
        int negVal = -1;
        CRLNumberExtension extNeg = new CRLNumberExtension(negVal);
        BigInteger valNeg = extNeg.getNumber();
        if (!BigInteger.valueOf(negVal).equals(valNeg)) {
            throw new RuntimeException("Expected " + negVal + " but got " + valNeg);
        }
    }
}
