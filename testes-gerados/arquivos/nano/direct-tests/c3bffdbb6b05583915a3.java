/* @test    @summary Test constructor BigInteger(int signum, byte[] magnitude) with various inputs    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testPositiveSignum();
        testZeroSignum();
        testNegativeSignum();
        testNullMagnitude();
        testEmptyMagnitude();
        testLargeMagnitude();
    }

    static void testPositiveSignum() {
        byte[] magnitude = {1, 2, 3};
        BigInteger bi = new BigInteger(1, magnitude);
        assert bi.signum() == 1 : "Expected signum 1";
        assert bi.equals(new BigInteger(1, magnitude)) : "Expected equal BigInteger";
    }

    static void testZeroSignum() {
        byte[] magnitude = {0, 0, 0};
        BigInteger bi = new BigInteger(0, magnitude);
        assert bi.signum() == 0 : "Expected signum 0";
        assert bi.equals(new BigInteger(0, magnitude)) : "Expected equal BigInteger";
    }

    static void testNegativeSignum() {
        byte[] magnitude = {4, 5, 6};
        BigInteger bi = new BigInteger(-1, magnitude);
        assert bi.signum() == -1 : "Expected signum -1";
        assert bi.equals(new BigInteger(-1, magnitude)) : "Expected equal BigInteger";
    }

    static void testNullMagnitude() {
        try {
            new BigInteger(1, (byte[]) null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testEmptyMagnitude() {
        byte[] magnitude = {};
        BigInteger bi = new BigInteger(1, magnitude);
        assert bi.signum() == 0 : "Expected signum 0 for empty magnitude";
        assert bi.equals(new BigInteger(1, magnitude)) : "Expected equal BigInteger";
    }

    static void testLargeMagnitude() {
        byte[] magnitude = new byte[1024];
        for (int i = 0; i < magnitude.length; i++) {
            magnitude[i] = (byte) (i % 256);
        }
        BigInteger bi = new BigInteger(1, magnitude);
        assert bi.signum() == 1 : "Expected signum 1 for large magnitude";
        assert bi.equals(new BigInteger(1, magnitude)) : "Expected equal BigInteger";
    }
}
