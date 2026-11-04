/* @test    @summary Test BigInteger(byte[] val) constructor with various byte arrays    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testEmptyArray();
        testPositiveNumber();
        testNegativeNumber();
        testZeroArray();
        testLargeNumber();
    }

    static void testEmptyArray() {
        byte[] empty = new byte[0];
        BigInteger bi = new BigInteger(empty);
        assert bi != null : "BigInteger should be created from empty array";
        assert bi.equals(BigInteger.ZERO) : "Empty array should result in BigInteger.ZERO";
    }

    static void testPositiveNumber() {
        byte[] positiveBytes = {1, 2, 3};
        BigInteger bi = new BigInteger(positiveBytes);
        BigInteger expected = new BigInteger("010203", 16);
        assert bi.equals(expected) : "BigInteger from positive bytes does not match expected";
    }

    static void testNegativeNumber() {
        byte[] negativeBytes = {(byte)0xFF, (byte)0xFE, (byte)0xFD};
        BigInteger bi = new BigInteger(negativeBytes);
        BigInteger expected = new BigInteger("FFFefD", 16);
        assert bi.equals(expected) : "BigInteger from negative bytes does not match expected";
    }

    static void testZeroArray() {
        byte[] zeroBytes = {0, 0, 0};
        BigInteger bi = new BigInteger(zeroBytes);
        assert bi.equals(BigInteger.ZERO) : "Array of zeros should result in BigInteger.ZERO";
    }

    static void testLargeNumber() {
        byte[] largeBytes = new byte[128];
        for (int i = 0; i < 128; i++) {
            largeBytes[i] = (byte)(i + 1);
        }
        BigInteger bi = new BigInteger(largeBytes);
        assert bi != null : "BigInteger should be created from large byte array";
    }
}
