/*
 * @test
 * @summary Test BigInteger(int signum, byte[] magnitude) constructor for valid and invalid signum values
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testValidSignum();
        testInvalidSignum();
    }

    private static void testValidSignum() {
        // signum = 0, magnitude empty array
        byte[] magEmpty = new byte[0];
        BigInteger biZero = new BigInteger(0, magEmpty);
        if (biZero.signum() != 0) {
            throw new RuntimeException("Expected signum 0 for zero magnitude");
        }

        // signum = 1, magnitude non-empty
        byte[] magPos = {1, 2, 3};
        BigInteger biPos = new BigInteger(1, magPos);
        if (biPos.signum() != 1) {
            throw new RuntimeException("Expected signum 1 for positive signum");
        }

        // signum = -1, magnitude non-empty
        byte[] magNeg = {4, 5, 6};
        BigInteger biNeg = new BigInteger(-1, magNeg);
        if (biNeg.signum() != -1) {
            throw new RuntimeException("Expected signum -1 for negative signum");
        }
    }

    private static void testInvalidSignum() {
        byte[] mag = {7, 8, 9};
        // According to WIT expath, abs(signum) > 1 should throw NumberFormatException
        // The constructor calls BigInteger(int, byte[], int, int) which throws NumberFormatException
        // We test signum = 2 and signum = -2
        try {
            new BigInteger(2, mag);
            throw new RuntimeException("Expected NumberFormatException for signum=2");
        } catch (NumberFormatException e) {
            // expected
        }

        try {
            new BigInteger(-2, mag);
            throw new RuntimeException("Expected NumberFormatException for signum=-2");
        } catch (NumberFormatException e) {
            // expected
        }
    }
}
