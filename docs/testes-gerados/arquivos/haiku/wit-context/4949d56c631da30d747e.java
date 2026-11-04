/*
 * @test
 * @summary WITUP-derived behavioral tests for BigInteger.setBit
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testNegativeBitAddressThrows();
        testSetBitOnZero();
        testSetBitIsIdempotent();
        testSetBitOnNegativeOperand();
        testSetBitLargeIndex();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNegativeBitAddressThrows() {
        int[] badIndexes = {-1, Integer.MIN_VALUE};
        for (int n : badIndexes) {
            boolean thrown = false;
            try {
                BigInteger.ZERO.setBit(n);
            } catch (ArithmeticException e) {
                thrown = true;
            }
            check(thrown, "setBit(" + n + ") must throw ArithmeticException");
        }
    }

    static void testSetBitOnZero() {
        check(BigInteger.ZERO.setBit(0).equals(BigInteger.valueOf(1)), "0 setBit(0) must be 1");
        check(BigInteger.ZERO.setBit(5).equals(BigInteger.valueOf(32)), "0 setBit(5) must be 32");
        check(BigInteger.ZERO.setBit(7).equals(BigInteger.valueOf(128)), "0 setBit(7) must be 128");
    }

    static void testSetBitIsIdempotent() {
        BigInteger v = BigInteger.valueOf(0b1010);
        check(v.setBit(1).equals(v), "setting an already set bit must not change value");
        check(v.setBit(3).equals(v), "setting an already set bit must not change value");
        check(v.setBit(0).equals(BigInteger.valueOf(0b1011)), "setBit(0) on 10 must be 11");
    }

    static void testSetBitOnNegativeOperand() {
        check(BigInteger.valueOf(-2).setBit(0).equals(BigInteger.valueOf(-1)),
              "-2 setBit(0) must be -1 in two's complement");
        check(BigInteger.valueOf(-1).setBit(1).equals(BigInteger.valueOf(-1)),
              "-1 setBit(1) must remain -1");
    }

    static void testSetBitLargeIndex() {
        check(BigInteger.ZERO.setBit(40).equals(BigInteger.ONE.shiftLeft(40)),
              "0 setBit(40) must equal 1 << 40");
        check(BigInteger.ONE.setBit(100).equals(BigInteger.ONE.shiftLeft(100).add(BigInteger.ONE)),
              "1 setBit(100) must equal 2^100 + 1");
    }
}
