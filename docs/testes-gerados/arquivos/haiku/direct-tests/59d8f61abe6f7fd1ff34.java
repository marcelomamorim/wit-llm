/*
 * @test
 * @summary Witup-style tests for the public BigInteger(byte[]) constructor
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testPositiveTwoBytes();
        testNegativeSingleByte();
        testLeadingZeroKeepsPositiveSign();
        testMinSingleByteIsNegative();
        testMaxSingleBytePositive();
        testZeroByte();
        testEquivalentToOffsetConstructor();
        testNullArrayThrowsNullPointerException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPositiveTwoBytes() {
        BigInteger b = new BigInteger(new byte[]{0x01, 0x02});
        check(b.equals(BigInteger.valueOf(258)), "expected 258 but got " + b);
        check(b.signum() == 1, "expected positive signum");
    }

    static void testNegativeSingleByte() {
        BigInteger b = new BigInteger(new byte[]{(byte) 0xFF});
        check(b.equals(BigInteger.valueOf(-1)), "expected -1 but got " + b);
        check(b.signum() == -1, "expected negative signum");
    }

    static void testLeadingZeroKeepsPositiveSign() {
        BigInteger b = new BigInteger(new byte[]{0x00, (byte) 0x80});
        check(b.equals(BigInteger.valueOf(128)), "expected 128 but got " + b);
        check(b.signum() == 1, "expected positive signum");
    }

    static void testMinSingleByteIsNegative() {
        BigInteger b = new BigInteger(new byte[]{(byte) 0x80});
        check(b.equals(BigInteger.valueOf(-128)), "expected -128 but got " + b);
        check(b.toString().equals("-128"), "unexpected toString: " + b);
    }

    static void testMaxSingleBytePositive() {
        BigInteger b = new BigInteger(new byte[]{0x7F});
        check(b.equals(BigInteger.valueOf(127)), "expected 127 but got " + b);
        check(b.bitLength() == 7, "expected bitLength 7 but got " + b.bitLength());
    }

    static void testZeroByte() {
        BigInteger b = new BigInteger(new byte[]{0x00});
        check(b.signum() == 0, "expected zero signum");
        check(b.equals(BigInteger.ZERO), "expected ZERO but got " + b);
    }

    static void testEquivalentToOffsetConstructor() {
        byte[] arr = {0x12, (byte) 0x34, (byte) 0x56};
        BigInteger full = new BigInteger(arr);
        BigInteger ranged = new BigInteger(arr, 0, arr.length);
        check(full.equals(ranged), "constructor(byte[]) differs from constructor(byte[],0,len)");
        check(full.equals(BigInteger.valueOf(0x123456)), "expected 0x123456 but got " + full);
    }

    static void testNullArrayThrowsNullPointerException() {
        try {
            new BigInteger((byte[]) null);
            throw new RuntimeException("expected NullPointerException for null array");
        } catch (NullPointerException expected) {
            // expected: val.length is dereferenced before delegation
        }
    }
}
