/*
 * @test
 * @summary Tests BigInteger.setBit observable behavior, including the negative bit address exception
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testNegativeBitAddressThrows();
        testSetBitOnZero();
        testSetBitOnTwo();
        testSetBitAlreadySet();
        testSetBitHighPosition();
        testNegativeOneUnchanged();
        testNegativeTwoSetBitZero();
        testOriginalIsImmutable();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNegativeBitAddressThrows() {
        boolean thrown = false;
        try {
            BigInteger.ONE.setBit(-1);
        } catch (ArithmeticException e) {
            thrown = true;
            check("Negative bit address".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
        check(thrown, "setBit(-1) should throw ArithmeticException");
    }

    static void testSetBitOnZero() {
        check(BigInteger.ZERO.setBit(0).equals(BigInteger.ONE),
              "0 setBit(0) should be 1");
    }

    static void testSetBitOnTwo() {
        check(BigInteger.valueOf(2).setBit(0).equals(BigInteger.valueOf(3)),
              "2 setBit(0) should be 3");
    }

    static void testSetBitAlreadySet() {
        check(BigInteger.valueOf(5).setBit(0).equals(BigInteger.valueOf(5)),
              "5 setBit(0) should remain 5");
    }

    static void testSetBitHighPosition() {
        BigInteger expected = BigInteger.ONE.shiftLeft(40);
        check(BigInteger.ZERO.setBit(40).equals(expected),
              "0 setBit(40) should be 2^40");
        BigInteger expected2 = BigInteger.ONE.shiftLeft(100).add(BigInteger.ONE);
        check(BigInteger.ONE.setBit(100).equals(expected2),
              "1 setBit(100) should be 2^100 + 1");
    }

    static void testNegativeOneUnchanged() {
        check(BigInteger.valueOf(-1).setBit(3).equals(BigInteger.valueOf(-1)),
              "-1 setBit(3) should remain -1");
    }

    static void testNegativeTwoSetBitZero() {
        check(BigInteger.valueOf(-2).setBit(0).equals(BigInteger.valueOf(-1)),
              "-2 setBit(0) should be -1");
    }

    static void testOriginalIsImmutable() {
        BigInteger original = BigInteger.valueOf(8);
        BigInteger result = original.setBit(0);
        check(original.equals(BigInteger.valueOf(8)), "original must not change");
        check(result.equals(BigInteger.valueOf(9)), "8 setBit(0) should be 9");
    }
}
