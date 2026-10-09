/*
 * @test
 * @summary Test BigInteger.byteValueExact() for correct behavior and exception
 * @run main BigIntegerWitupTest_Pf50ad4709774
 */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pf50ad4709774 {
    public static void main(String[] args) {
        testByteValueExactInRange();
        testByteValueExactOutOfRange();
    }

    private static void testByteValueExactInRange() {
        // Values within byte range [-128, 127]
        BigInteger[] inRange = {
            BigInteger.valueOf(0),
            BigInteger.valueOf(127),
            BigInteger.valueOf(-128),
            BigInteger.valueOf(1),
            BigInteger.valueOf(-1)
        };
        for (BigInteger bi : inRange) {
            byte b = bi.byteValueExact();
            if (b != bi.byteValue()) {
                throw new RuntimeException("byteValueExact() returned " + b + " but expected " + bi.byteValue());
            }
        }
    }

    private static void testByteValueExactOutOfRange() {
        // Values outside byte range should throw ArithmeticException
        BigInteger[] outOfRange = {
            BigInteger.valueOf(128),
            BigInteger.valueOf(-129),
            new BigInteger("1000"), // 1000 > 127
            new BigInteger("-1000") // -1000 < -128
        };
        for (BigInteger bi : outOfRange) {
            boolean thrown = false;
            try {
                bi.byteValueExact();
            } catch (ArithmeticException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected ArithmeticException for value " + bi);
            }
        }
    }
}
