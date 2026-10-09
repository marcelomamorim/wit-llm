/* @test    @summary Testa o método Math.negateExact para verificar o comportamento de overflow.    @run main MathWitupTest_Pccdb99be93cc */

import java.lang.Math;

public class MathWitupTest_Pccdb99be93cc {

    public static void main(String[] args) {
        testNegateExactNormal();
        testNegateExactOverflow();
    }

    static void testNegateExactNormal() {
        long input = 123456789L;
        long expected = -123456789L;
        long result = Math.negateExact(input);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testNegateExactOverflow() {
        long input = Long.MIN_VALUE;
        boolean exceptionThrown = false;
        try {
            Math.negateExact(input);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ArithmeticException when input is Long.MIN_VALUE");
        }
    }
}
