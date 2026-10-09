/* @test    @summary Test for Math.negateExact method to verify correct negation and exception handling    @run main MathWitupTest_Pccdb99be93cc */

import java.lang.Math;

public class MathWitupTest_Pccdb99be93cc {

    public static void main(String[] args) {
        testNegateExactNormal();
        testNegateExactOverflow();
    }

    static void testNegateExactNormal() {
        long value = 123456789L;
        long result = Math.negateExact(value);
        assert result == -value : "Expected " + (-value) + " but got " + result;
    }

    static void testNegateExactOverflow() {
        long minLong = Long.MIN_VALUE;
        try {
            Math.negateExact(minLong);
            assert false : "Expected ArithmeticException for Long.MIN_VALUE";
        } catch (ArithmeticException e) {
            assert e.getMessage().equals("long overflow") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
