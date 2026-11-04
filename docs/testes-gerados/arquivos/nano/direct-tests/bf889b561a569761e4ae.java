/* @test    @summary Test constructor BigInteger(int, int, Random) for valid and invalid inputs    @run main BigIntegerWitupTest */

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testInvalidBitLength();
    }

    static void testValidConstructor() {
        Random rnd = new Random(12345);
        int bitLength = 16;
        int certainty = 10;
        BigInteger bi = new BigInteger(bitLength, certainty, rnd);
        assert bi != null : "Constructor returned null";
        assert bi.signum() > 0 : "BigInteger should be positive";
        assert bi.bitLength() >= bitLength - 1 : "Bit length is less than expected";
    }

    static void testInvalidBitLength() {
        Random rnd = new Random(12345);
        int[] invalidBitLengths = {0, 1};
        for (int bl : invalidBitLengths) {
            try {
                new BigInteger(bl, 10, rnd);
                assert false : "Expected ArithmeticException for bitLength=" + bl;
            } catch (ArithmeticException e) {
                assert e.getMessage().contains("bitLength < 2") : "Unexpected exception message: " + e.getMessage();
            }
        }
    }
}
