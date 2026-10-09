/* @test    @summary Test for BigInteger.clearBit(int n) handling negative input and valid input    @run main BigIntegerWitupTest_P1b223680f8d9 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P1b223680f8d9 {

    public static void main(String[] args) {
        testClearBitNegative();
        testClearBitValid();
    }

    private static void testClearBitNegative() {
        BigInteger bi = BigInteger.valueOf(12345L);
        try {
            bi.clearBit(-1);
            System.err.println("FAIL: Expected ArithmeticException for negative n");
        } catch (ArithmeticException e) {
            System.out.println("PASS: Caught expected ArithmeticException for negative n");
        } catch (Exception e) {
            System.err.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testClearBitValid() {
        BigInteger bi = new BigInteger("1024"); // 2^10
        int n = 10;
        BigInteger result = bi.clearBit(n);
        BigInteger expected = new BigInteger("1024"); // clearing bit 10 of 1024 (which is set)
        // Since bit 10 is set in 1024, clearing it should result in 0
        BigInteger expectedCleared = BigInteger.valueOf(0);
        if (result.equals(expectedCleared)) {
            System.out.println("PASS: clearBit correctly cleared bit " + n);
        } else {
            System.err.println("FAIL: clearBit did not clear bit " + n + ", got: " + result);
        }
        // Additional test: clearing a bit not set should leave number unchanged
        BigInteger bi2 = new BigInteger("512"); // 2^9
        int n2 = 10;
        BigInteger result2 = bi2.clearBit(n2); // bit 10 not set in 512
        if (result2.equals(bi2)) {
            System.out.println("PASS: clearBit on unset bit " + n2 + " leaves number unchanged");
        } else {
            System.err.println("FAIL: clearBit on unset bit " + n2 + " changed number to: " + result2);
        }
    }
}
