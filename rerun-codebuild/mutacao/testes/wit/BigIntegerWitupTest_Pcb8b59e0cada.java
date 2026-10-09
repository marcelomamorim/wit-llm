/* @test    @summary Test for BigInteger.flipBit(int n) method, including exception path for negative input    @run main BigIntegerWitupTest_Pcb8b59e0cada */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pcb8b59e0cada {

    public static void main(String[] args) {
        testFlipBitWithValidInput();
        testFlipBitWithNegativeInput();
    }

    private static void testFlipBitWithValidInput() {
        BigInteger bi = BigInteger.valueOf(0);
        try {
            BigInteger result = bi.flipBit(0);
            System.out.println("flipBit(0): " + result);
        } catch (Exception e) {
            System.err.println("Unexpected exception for flipBit(0): " + e);
        }
    }

    private static void testFlipBitWithNegativeInput() {
        BigInteger bi = BigInteger.valueOf(0);
        try {
            BigInteger result = bi.flipBit(-1);
            System.err.println("Expected exception not thrown for flipBit(-1)");
        } catch (ArithmeticException e) {
            System.out.println("Correctly caught ArithmeticException for flipBit(-1): " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected exception type for flipBit(-1): " + e);
        }
    }
}
