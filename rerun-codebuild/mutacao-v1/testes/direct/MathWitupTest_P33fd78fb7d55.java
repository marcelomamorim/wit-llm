/* @test    @summary Test decrementExact method for boundary and normal cases    @run main MathWitupTest_P33fd78fb7d55 */

import java.lang.Math;

public class MathWitupTest_P33fd78fb7d55 {

    public static void main(String[] args) {
        testDecrementExactNormal();
        testDecrementExactMinValue();
        System.out.println("All tests passed.");
    }

    private static void testDecrementExactNormal() {
        long input = 100L;
        long expected = 99L;
        long result = Math.decrementExact(input);
        if (result != expected) {
            throw new AssertionError("Failed: decrementExact(" + input + ") expected " + expected + " but got " + result);
        }
    }

    private static void testDecrementExactMinValue() {
        long input = Long.MIN_VALUE;
        try {
            Math.decrementExact(input);
            throw new AssertionError("Expected ArithmeticException for input Long.MIN_VALUE");
        } catch (ArithmeticException e) {
            if (!"long overflow".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
