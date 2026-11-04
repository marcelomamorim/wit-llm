/*
 * @test
 * @summary Test BigInteger(int signum, byte[] magnitude) constructor behavior
 * @run main java.math.BigIntegerWitupTest
 */

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testZeroMagnitudeZeroSignum();
        testPositiveSignumPositiveMagnitude();
        testNegativeSignumPositiveMagnitude();
        testZeroMagnitudePositiveSignum();
        testEmptyMagnitudeArray();
    }

    private static void testZeroMagnitudeZeroSignum() {
        byte[] mag = {0, 0, 0};
        java.math.BigInteger bi = new java.math.BigInteger(0, mag);
        if (bi.signum() != 0) {
            throw new RuntimeException("Expected signum 0 for zero magnitude");
        }
        if (!bi.toByteArray()[0].equals((byte)0) && bi.toByteArray().length != 1) {
            throw new RuntimeException("Expected byte array representing zero");
        }
    }

    private static void testPositiveSignumPositiveMagnitude() {
        byte[] mag = {1, 2, 3};
        java.math.BigInteger bi = new java.math.BigInteger(1, mag);
        if (bi.signum() != 1) {
            throw new RuntimeException("Expected positive signum");
        }
        byte[] expectedMag = {1, 2, 3};
        byte[] actualMag = bi.abs().toByteArray();
        // The magnitude may have a leading zero byte if highest bit is set, so check endsWith
        if (!endsWith(actualMag, expectedMag)) {
            throw new RuntimeException("Magnitude bytes do not match expected");
        }
    }

    private static void testNegativeSignumPositiveMagnitude() {
        byte[] mag = {4, 5, 6};
        java.math.BigInteger bi = new java.math.BigInteger(-1, mag);
        if (bi.signum() != -1) {
            throw new RuntimeException("Expected negative signum");
        }
        byte[] expectedMag = {4, 5, 6};
        byte[] actualMag = bi.abs().toByteArray();
        if (!endsWith(actualMag, expectedMag)) {
            throw new RuntimeException("Magnitude bytes do not match expected");
        }
    }

    private static void testZeroMagnitudePositiveSignum() {
        byte[] mag = {0, 0};
        java.math.BigInteger bi = new java.math.BigInteger(1, mag);
        if (bi.signum() != 0) {
            throw new RuntimeException("Expected signum 0 for zero magnitude even if signum param is positive");
        }
    }

    private static void testEmptyMagnitudeArray() {
        byte[] mag = new byte[0];
        java.math.BigInteger bi = new java.math.BigInteger(1, mag);
        if (bi.signum() != 0) {
            throw new RuntimeException("Expected signum 0 for empty magnitude array");
        }
    }

    private static boolean endsWith(byte[] array, byte[] suffix) {
        if (array.length < suffix.length) {
            return false;
        }
        for (int i = 1; i <= suffix.length; i++) {
            if (array[array.length - i] != suffix[suffix.length - i]) {
                return false;
            }
        }
        return true;
    }
}
