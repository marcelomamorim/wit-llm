/*
 * @test
 * @summary Witup-generated checks for the BigInteger(byte[] val) constructor
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testEmptyArrayThrowsNumberFormatException();
        testSignedTwosComplementValues();
        testToByteArrayRoundTrip();
    }

    static void testEmptyArrayThrowsNumberFormatException() {
        try {
            new BigInteger(new byte[0]);
            throw new AssertionError("expected NumberFormatException for empty array");
        } catch (NumberFormatException expected) {
        }
    }

    static void testSignedTwosComplementValues() {
        check(new BigInteger(new byte[]{0x01, 0x00}), 256L);
        check(new BigInteger(new byte[]{(byte) 0xFF}), -1L);
        check(new BigInteger(new byte[]{0x7F}), 127L);
        check(new BigInteger(new byte[]{(byte) 0x80}), -128L);
        check(new BigInteger(new byte[]{0x00, (byte) 0x80}), 128L);
    }

    static void testToByteArrayRoundTrip() {
        BigInteger[] values = {
            BigInteger.ZERO,
            BigInteger.ONE,
            BigInteger.valueOf(-1L),
            BigInteger.valueOf(Long.MIN_VALUE),
            BigInteger.valueOf(Long.MAX_VALUE)
        };
        for (BigInteger v : values) {
            BigInteger copy = new BigInteger(v.toByteArray());
            if (!copy.equals(v)) {
                throw new AssertionError("round trip failed for " + v);
            }
        }
    }

    static void check(BigInteger actual, long expected) {
        BigInteger exp = BigInteger.valueOf(expected);
        if (!actual.equals(exp)) {
            throw new AssertionError("expected " + exp + " but was " + actual);
        }
    }
}
