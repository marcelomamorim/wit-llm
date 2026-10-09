/* @test    @summary Test for Math.incrementExact method to verify correct increment and overflow exception handling    @run main MathWitupTest_P734804c51965 */

public class MathWitupTest_P734804c51965 {

    public static void main(String[] args) {
        testIncrementExactNormal();
        testIncrementExactOverflow();
    }

    static void testIncrementExactNormal() {
        long input = 42L;
        long expected = 43L;
        long result = Math.incrementExact(input);
        if (result != expected) {
            throw new AssertionError("Failed: incrementExact(" + input + ") = " + result + ", expected " + expected);
        }
    }

    static void testIncrementExactOverflow() {
        long input = Long.MAX_VALUE;
        boolean exceptionThrown = false;
        try {
            Math.incrementExact(input);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Failed: incrementExact(" + input + ") did not throw ArithmeticException as expected");
        }
    }
}
